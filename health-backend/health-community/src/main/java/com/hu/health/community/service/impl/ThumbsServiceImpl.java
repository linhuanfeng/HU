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
        //TODO ????????????
        // ?????????????????????id
        String prefixUser = ArticleConstants.ARTICLE_THUMB_USER + thumbs.getEntityId();
        BoundSetOperations<String, String> setOperations = redisTemplate.boundSetOps(prefixUser);
        setOperations.expire(3, TimeUnit.HOURS);
        setOperations.add(thumbs.getMemberId().toString());

        // ????????????????????????
        BoundSetOperations<String, String> setOperations2 = redisTemplate.boundSetOps(ArticleConstants.ARTICLE_THUMB_ARTICLES);
        setOperations2.expire(3, TimeUnit.HOURS);
        setOperations2.add(thumbs.getEntityId().toString());

    }

    @Override
    public void removeRedis(ThumbsEntity thumb) {
        // ??????redis
        String prefix = ArticleConstants.ARTICLE_THUMB_USER + thumb.getEntityId();
//        // set: postId,memberId
        redisTemplate.boundSetOps(prefix).remove(thumb.getMemberId().toString());
    }

    /**
     * ??????????????????????????????
     *
     * @param entity
     * @return
     */
//    @GlobalTransactional // ?????????????????????
    @Transactional
    @Override
    public boolean saveEntity(ThumbsEntity entity) {
        // ????????????????????????
        questionService.thumb(entity.getEntityId());
        // ??????????????????
        this.baseMapper.insert(entity);
        // ??????????????????????????????
        memberFeignService.addLikeCount(new MemberEntityTo(entity.getMemberId(), 1));
//        int i=1/0;
        return true;
    }

    @Override
    public boolean saveEntityRedis(ThumbsEntity entity) {
        // ??????id?????????id
        Long entityId = entity.getEntityId();
        // 0???????????????1????????????
        int type = entity.getType();
        // ??????redis???set??????key(set:thumb:entityId:type)  value(memberId)
        String key = "set:thumb:" + entityId + ":" + type;
        String value=String.valueOf(entity.getMemberId());
        BoundSetOperations<String, String> setOps = redisTemplate.boundSetOps(key);
        setOps.add(value);
        // ???????????????????????????mysql
        return true;
    }

    /**
     * ?????????????????????
     *
     * @param entity
     * @return
     */
//    @GlobalTransactional // ??????????????????
    @Transactional
    @Override
    public boolean removeEntity(ThumbsEntity entity) {
        log.info("30???????????????,?????????????????????");
        // ???????????????????????????20s????????????
        scheduledExecutorService.schedule(
                ()->{
                    log.info("????????????");
                    // ????????????????????????
                    int delete = this.baseMapper.delete(new UpdateWrapper<ThumbsEntity>().
                            eq("member_id", entity.getMemberId())
                            .eq("entity_id", entity.getEntityId())
                            .eq("type", entity.getType()));
                    if(delete>0){
                        // ??????????????????????????????
                        memberFeignService.subLikeCount(new MemberEntityTo(entity.getMemberId(), 1)); // ?????????????????????
                        // int i=1/0;  // ?????????????????????????????????
                        if(entity.getType()== ThumbsEntity.EntityType.questionType.getKey()){
                            // ????????????????????????
                            questionService.unThumb(entity.getEntityId());
                        } else if (entity.getType()== ThumbsEntity.EntityType.commentType.getKey()){
                            // ????????????????????????
                            commentService.unThumb(entity.getEntityId());
                        }
                    }else {
                        log.warn("????????????????????????{}",entity);
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