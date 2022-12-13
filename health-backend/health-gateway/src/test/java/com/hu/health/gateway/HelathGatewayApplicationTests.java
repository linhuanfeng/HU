package com.hu.health.gateway;

import com.hu.health.gateway.utils.AuthUserInfoTo;
import com.hu.health.gateway.utils.JwtRsaUtil;
import com.hu.health.gateway.utils.PayLoad;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class HelathGatewayApplicationTests {

    @Test
    void testGenerateToken() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        PayLoad payLoad = new PayLoad();
        payLoad.setUserInfo(new AuthUserInfoTo(2l,"moon"));
        String token = JwtRsaUtil.generateJwtTokenHMAC(payLoad);
        System.out.println(token);
        System.out.println(JwtRsaUtil.getInfoFromToken(token));
    }
}
