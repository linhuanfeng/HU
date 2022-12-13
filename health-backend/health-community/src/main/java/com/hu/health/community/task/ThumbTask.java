package com.hu.health.community.task;

import com.hu.health.community.constants.ArticleConstants;
import com.hu.health.community.entity.ThumbsEntity;
import com.hu.health.community.service.QuestionService;
import com.hu.health.community.service.ThumbsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Deprecated
//@Component // 把目标对象放入容器，后面jobDetail工厂会包装和调用本实例
//@EnableScheduling // 开启spring-scheduled功能 Enables Spring's scheduled task execution capability
public class ThumbTask{

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ThumbsService thumbsService;

    @Autowired
    QuestionService questionService;

    protected void thumbToDB()  {
//        log.info("同步到数据库中");
        // 所有被点赞的文章id
        Set<String> articleIds = redisTemplate.boundSetOps(ArticleConstants.ARTICLE_THUMB_ARTICLES).members();

        // 批量插入到点赞表
        for (String articleId : articleIds) {

            // 获取缓存：获取文章articleId的所有点赞用户memberIds
            String prefixUser=ArticleConstants.ARTICLE_THUMB_USER+articleId;
            Set<String> memberIds = redisTemplate.boundSetOps(prefixUser).members();
            if(memberIds.isEmpty()){
                continue;
            }

            // 持久化：批量插入到数据库用户点赞表
            List<ThumbsEntity> thumbsEntities=new ArrayList<>();
            for (String memberId : memberIds) {
                ThumbsEntity thumbsEntity = new ThumbsEntity();
                thumbsEntity.setEntityId(Long.parseLong(articleId));
                thumbsEntity.setMemberId(Long.parseLong(memberId));
                thumbsEntities.add(thumbsEntity);
            }
            thumbsService.saveBatch(thumbsEntities);

            // 持久化：增加文章的点赞量
            Long thumbSize = redisTemplate.boundSetOps(prefixUser).size();
//            questionService.thumbs(Long.parseLong(articleId),thumbSize);

            // 删除缓存：文章articleId的所有点赞用户memberIds(srem不能为空)
            redisTemplate.boundSetOps(prefixUser).remove(memberIds.toArray());
            // 被点赞表缓存等自动过期
        }
    }
}
