package com.hu.health.gateway.filter;

import com.google.gson.JsonObject;
import com.hu.health.gateway.utils.JwtRsaUtil;
import com.hu.health.gateway.utils.PayLoad;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
//@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if(antPathMatcher.match("/api/ums/wx/decryptInfo",path)){
            // 登录的路径，放行请求
        }else {
            // 全部都得携带token
            List<String> tokenList = request.getHeaders().get("auth_token");
            ServerHttpResponse response = exchange.getResponse();
            if(null == tokenList) {
                return out(response,"请携带登录token");
            } else {
                // 验证token是否合法,如果合法得到负载
                try {
                    PayLoad payLoad = JwtRsaUtil.getInfoFromToken(tokenList.get(0));
                    //TODO 取redis中用户的详细信息
                } catch (NoSuchAlgorithmException e) {
                    log.error("认证失败，服务器内部算法异常{}",e.getMessage());
                    return out(response,"认证失败，服务器内部算法异常");
                } catch (UnsupportedEncodingException e) {
                    log.error("认证失败，token无效{}",e.getMessage());
                    return out(response);
                }catch (Exception e){
                    log.error("认证失败，token无效{}",e.getMessage());
                    return out(response);
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        return out(response,"认证失败，token无效");
    }
    private Mono<Void> out(ServerHttpResponse response,String errmsg) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 28004);
        message.addProperty("data", errmsg);
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
