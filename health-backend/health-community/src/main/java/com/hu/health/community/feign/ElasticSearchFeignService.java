package com.hu.health.community.feign;

import com.hu.health.common.pojo.SearchParam;
import com.hu.health.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.ExecutionException;
@FeignClient(name = "health-search")
public interface ElasticSearchFeignService {
    /**
     * 根据关键字查询帖子
     * 需要传入参数：关键字、分区、标签；索引自带：点赞数和浏览次数
     * 基于ElasticSearch的function_score，综合社区帖子的热度（点赞和浏览数）和用户历史浏览搜索数据（关键字，分区和标签）
     * @param searchParam
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/search/search/search")
    R search(@RequestBody SearchParam searchParam) throws ExecutionException, InterruptedException;
}
