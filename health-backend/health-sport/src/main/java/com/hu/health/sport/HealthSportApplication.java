package com.hu.health.sport;

import com.lhf.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients("com.hu.health.sport.feign")
@EnableDiscoveryClient
@EnableCanalClient
@SpringBootApplication
@ComponentScan("com.hu.health")
public class HealthSportApplication {
/*asdad*/
    public static void main(String[] args) {
        SpringApplication.run(HealthSportApplication.class, args);
    }

}
