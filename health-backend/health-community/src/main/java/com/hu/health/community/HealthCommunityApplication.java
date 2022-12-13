package com.hu.health.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableRabbit // 让@RabbitListener注解生效
@EnableFeignClients("com.hu.health.community.feign")  // 扫描@FeignClient和@Configuration
@EnableDiscoveryClient
//@EnableCanalClient // 暂时关闭canal
//@EnableCaching // 开启缓存@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.hu.health.community.dao")
@SpringBootApplication
public class HealthCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCommunityApplication.class, args);
	}
}
