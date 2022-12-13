package com.hu.health.selfdiagonsis;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//
@EnableDiscoveryClient
@SpringBootApplication
public class HealthSelfdiagonsisApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthSelfdiagonsisApplication.class, args);
    }

}
