package com.hu.health.diet.openClient;

import com.hu.health.common.utils.R;
//import com.hu.health.sport.entity.MemberEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("health-member")
public interface UserClient {

    @GetMapping("/ums/member/info/{id}")
    R findById(@PathVariable(value="id") Long id);
}
