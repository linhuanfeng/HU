package com.hu.health.search.config;

import com.hu.health.search.filter.CharacterEncodingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 无效还是无法解决
 */
//@Configuration
public class WebMvcConfig {

    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        // 添加自定义字符过滤器
        registrationBean.setFilter(new CharacterEncodingFilter());
        registrationBean.addUrlPatterns("/**");
        registrationBean.setName("CharacterEncodingFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
