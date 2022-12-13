package com.hu.health.sport.feign;

import com.hu.health.common.utils.R;
//import com.hu.health.sport.controller.R;
import com.hu.health.sport.feign.fallback.MemberFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "health-member",fallbackFactory = MemberFeignFallbackFactory.class)
public interface MemberFeignService {
    /**
     * 信息
     */
    @RequestMapping("/ums/member/info/{id}")
    // @RequiresPermissions("sport:member:info")
    public R info(@PathVariable("id") Long id);
}
