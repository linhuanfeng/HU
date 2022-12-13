package com.hu.health.sport.config;

//import com.hu.health.common.utils.R;
import com.hu.health.sport.feign.fallback.MemberFeignFallbackFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class MemberFeignConfig {
    @Bean
    public MemberFeignFallbackFactory memberFeignFallbackFactory(){
        return new MemberFeignFallbackFactory();
    }
}
