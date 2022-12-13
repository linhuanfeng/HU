package com.hu.health.member.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WXConfig.class,ApplicationValuesConfig.class}) // 开启属性类的自动配置
public class AutoConfig {
}