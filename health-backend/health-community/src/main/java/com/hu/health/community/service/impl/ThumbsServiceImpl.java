package com.hu.health.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hu.health.common.MqConstants;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.community.constants.ArticleConstants;
import com.hu.health.community.feign.MemberFeignService;
import com.hu.health.community.service.CommentService;
import com.hu.health.community.service.QuestionService;
import com.hu.health.community.to.MemberEntityTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hu.health.community.dao.ThumbsDao;
import com.hu.health.community.entity.ThumbsEntity;
import com.hu.health.community.service.ThumbsService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.plaf.TableHeaderUI;


@Service
@Slf4j
public class ThumbsServiceImpl extends ServiceImpl<ThumbsDao, ThumbsEntity> implements ThumbsService {

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //    @Autowired
    private QuestionService questionService;

    @Resource
    private CommentService commentService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private ScheduledExecutorService scheduledExecutorService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Lazy
    public ThumbsServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ThumbsEntity> page = this.page(
                new Query<ThumbsEntity>().getPage(params),
                new QueryWrapper<ThumbsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveRedis(ThumbsEntity thumbs) {
        //TODO 原子操作
        // 保存点赞的用户id
        String prefixUser = ArticleConstants.ARTICLE_THUMB_USER + thumbs.getEntityId();
        BoundSetOperations<String, String> setOperations = redisTemplate.boundSetOps(prefixUser);
        setOperations.expire(3, TimeUnit.HOURS);
        setOperations.add(thumbs.getMemberId().toString());

        // 保存被点赞的文章
        BoundSetOperations<String, String> setOperations2 = redisTemplate.boundSetOps(ArticleConstants.ARTICLE_THUMB_ARTICLES);
        setOperations2.expire(3, TimeUnit.HOURS);
        setOperations2.add(thumbs.getEntityId().toString());

    }

    @Override
    public void removeRedis(ThumbsEntity thumb) {
        // 存入redis
        String prefix = ArticleConstants.ARTICLE_THUMB_USER + thumb.getEntityId();
//        // set: postId,memberId
        redisTemplate.boundSetOps(prefix).remove(thumb.getMemberId().toString());
    }

    /**
     * 保存点赞，分布事事务
     *
     * @param entity
     * @return
     */
//    @GlobalTransactional // 全局事务的入口
    @Transactional
    @Override
    public boolean saveEntity(ThumbsEntity entity) {
        // 增加文章的点赞量
        questionService.thumb(entity.getEntityId());
        // 增加点赞记录
        this.baseMapper.insert(entity);
        // 远程增加作者的点赞数
        memberFeignService.addLikeCount(new MemberEntityTo(entity.getMemberId(), 1));
//        int i=1/0;
        return true;
    }

    @Override
    public boolean saveEntityRedis(ThumbsEntity entity) {
        // 帖子id或评论id
        Long entityId = entity.getEntityId();
        // 0表示帖子，1表示评论
        int type = entity.getType();
        // 存入redis的set中，key(set:thumb:entityId:type)  value(memberId)
        String key = "set:thumb:" + entityId + ":" + type;
        String value=String.valueOf(entity.getMemberId());
        BoundSetOperations<String, String> setOps = redisTemplate.boundSetOps(key);
        setOps.add(value);
        // 定时任务异步同步到mysql
        return true;
    }

    /**
     * 取消点赞，同时
     *
     * @param entity
     * @return
     */
//    @GlobalTransactional // 开启全局事务
    @Transactional
    @Override
    public boolean removeEntity(ThumbsEntity entity) {
        log.info("30秒后再执行,等待缓存刷新后");
        // 可能还在缓存中，等20s后再删除
        scheduledExecutorService.schedule(
                ()->{
                    log.info("开始执行");
                    // 删除本次点赞记录
                    int delete = this.baseMapper.delete(new UpdateWrapper<ThumbsEntity>().
                            eq("member_id", entity.getMemberId())
                            .eq("entity_id", entity.getEntityId())
                            .eq("type", entity.getType()));
                    if(delete>0){
                        // 远程取消作者的点赞数
                        memberFeignService.subLikeCount(new MemberEntityTo(entity.getMemberId(), 1)); // 远程调用不会滚
                        // int i=1/0;  // 模拟异常，本地事务回滚
                        if(entity.getType()== ThumbsEntity.EntityType.questionType.getKey()){
                            // 取消文章的点赞量
                            questionService.unThumb(entity.getEntityId());
                        } else if (entity.getType()== ThumbsEntity.EntityType.commentType.getKey()){
                            // 取消评论的点赞量
                            commentService.unThumb(entity.getEntityId());
                        }
                    }else {
                        log.warn("不存在该点赞记录{}",entity);
                    }
                },
                30*1000,TimeUnit.MILLISECONDS
        );

        return true;
    }

    @Override
    public void removeEntitiesByQuestionIds(List<Long> asList) {
        this.baseMapper.removeThumbsByQuestionIds(asList);
    }
}