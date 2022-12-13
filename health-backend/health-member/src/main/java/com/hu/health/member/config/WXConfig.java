package com.hu.health.member.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx")
public class WXConfig {
    String url;
    String appid;
    String secret;
    String grantType;
}
