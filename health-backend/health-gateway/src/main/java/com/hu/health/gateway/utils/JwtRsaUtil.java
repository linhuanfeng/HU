package com.hu.health.gateway.utils;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.Assert;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * jwt使用rsa非对称加解密工具类
 */
public class JwtRsaUtil {
    private static final String SECRET="sdfdsfs"; // 密匙
    public static String generateJwtTokenHMAC(PayLoad userInfoPayLoad) throws UnsupportedEncodingException {
        Assert.notNull(userInfoPayLoad,"userInfoPayLoad不能为空");
        return  JWT.create()
                    .withIssuer("HU")
                    .withClaim("userInfo", JSON.toJSONString(userInfoPayLoad))
                    .sign(Algorithm.HMAC256(SECRET));
    }

    public static PayLoad getInfoFromToken(String token) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer("HU")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Claim claim = jwt.getClaim("userInfo");
        String payload = claim.asString();
        PayLoad payLoad = JSON.parseObject(payload, PayLoad.class);
        return payLoad;
    }
}
