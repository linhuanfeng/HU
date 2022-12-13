package com.hu.health.consult;

import com.hu.health.consult.config.AliYunOssConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.hu.health")
@EnableConfigurationProperties({AliYunOssConfig.class})
public class HealthConsultApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthConsultApplication.class, args);
    }

}
