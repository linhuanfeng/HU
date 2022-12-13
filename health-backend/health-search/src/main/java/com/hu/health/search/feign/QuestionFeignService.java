package com.hu.health.search.feign;

import com.hu.health.common.utils.R;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("health-community")
public interface QuestionFeignService {
    /**
     * 从mysql获取帖子列表
     */
    @RequestMapping("/community/question/list")
    // @RequiresPermissions("community:question:list")
    R list(@RequestParam Map<String, Object> params);
    /**
     * 从mysql获取单个帖子
     */
    @RequestMapping("/community/question/info/{id}")
    R info(@PathVariable("id") Long id);
}
