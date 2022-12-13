package com.hu.health.community.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
//@EnableScheduling
public class DeleteQuestionCacheTask {
    @CacheEvict(cacheNames = "article",keyGenerator = "articleGenerator")
    public void deleteQuestionCache(){
//        log.info("删除了文章列表缓存");
    }
}
