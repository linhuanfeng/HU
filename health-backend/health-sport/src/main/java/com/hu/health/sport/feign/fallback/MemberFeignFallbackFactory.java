package com.hu.health.sport.feign.fallback;

import com.hu.health.common.utils.R;
import com.hu.health.sport.feign.MemberFeignService;
import org.springframework.cloud.openfeign.FallbackFactory;
//import feign.hystrix.FallbackFactory;

public class MemberFeignFallbackFactory implements FallbackFactory<MemberFeignService> {
//    @Override
    public MemberFeignService create(Throwable cause) {
        return id -> R.error("setinel处理"+cause.getMessage());
    }
}
