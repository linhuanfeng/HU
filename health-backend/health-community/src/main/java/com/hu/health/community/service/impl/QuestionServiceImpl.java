package com.hu.health.community.service.impl;

import com.hu.health.common.MqConstants;
import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.community.constants.QuestionConstants;
import com.hu.health.community.entity.CommentEntity;
import com.hu.health.community.feign.MemberFeignService;
import com.hu.health.community.service.CommentService;
import com.hu.health.community.service.ThumbsService;
import com.hu.health.community.to.MemberEntityTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hu.health.community.dao.QuestionDao;
import com.hu.health.community.service.QuestionService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, QuestionEntity> implements QuestionService {

    @Autowired
    private MemberFeignService memberFeignService;

    @Resource
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    @Qualifier(value = "") 结合Qualifier根据名称注入指定的bean
    private CommentService commentService;

//    @Autowired
    private ThumbsService thumbsService;

    @Resource
    private StringRedisTemplate redisTemplate;

    public QuestionServiceImpl(CommentService commentService,ThumbsService thumbsService)
    {
        this.commentService=commentService;
        this.thumbsService=thumbsService;
    }

    /**
     * 帖子点赞数加n
     * @param id
     * @param num
     */
    @Override
    public void thumbs(Long id,Long num) {
        baseMapper.thumb(id, Math.toIntExact(num));
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    /**
     * 尝试优化，先从缓存中取数据
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<QuestionEntity> queryWrapper = new QueryWrapper<>();
        if(params.get("type")!=null){
            queryWrapper.eq("type",params.get("type"));
        }
        if(params.get("areaId")!=null){
            queryWrapper.eq("area_id",params.get("areaId"));
        }
        IPage<QuestionEntity> page = this.page(
                new Query<QuestionEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }


    @Override
    public void thumb(Long id) {
//        hashIncr(id,"like_count",1);
        this.baseMapper.thumb(id,1);
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    @Override
    public void unThumb(Long id) {
//        hashIncr(id,"like_count",-1);
        this.baseMapper.unThumb(id,1);
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    /**
     * 文章评论数加一
     * @param id
     */
    @Override
    public void comment(Long id) {
        // redis更新点赞，再定期持久化到mysql中
//        hashIncr(id,"comment_count",1);
        // mysql点赞数增加
        this.baseMapper.comment(id,1);
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    /**
     * 更新es的帖子信息
     * @param id 帖子id
     * @param routingKey 更新还是删除
     */
    public void updateEs(Long id,String routingKey){
        // 更新es的帖子信息
        log.info("发送es,帖子id{}",id);
        rabbitTemplate.convertAndSend(MqConstants.QUESTION_EXCHANGE,routingKey,id);
    }

    /**
     * 文章评论数减一
     * @param id
     */
    @Override
    public void unComment(Long id) {
        // redis hash字段评论数减一
//        hashIncr(id,"comment_count",-1);
        // 评论计数减一
        this.baseMapper.unComment(id,1);
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    @Override
    public void thumb(Long id, int delta) {
        getBaseMapper().thumb(id, delta);
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    @Override
    public void unThumb(Long questionId, int delta) {
        getBaseMapper().unThumb(questionId, delta);
        // 更新es的帖子信息
        updateEs(questionId,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    @Override
    public void comment(Long id, int delta) {
        getBaseMapper().comment(id, delta);
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    @Override
    public void unComment(Long id, int delta) {
        getBaseMapper().unComment(id, delta);
        // 更新es的帖子信息
        updateEs(id,MqConstants.QUESTION_UPDATE_ROUTINGKEY);
    }

    /**
     * hash帖子的存储,增加点赞评论计数
     *
     * 再定期更新到mysql中
     *
     * key "hash:question:"+qid
     * hk like_count/comment_count
     *
     * hincryBy key field value
     *
     * 帖子的获取，获取整个hash对象，然后再进行封装生成对象
     *
     */
    private void hashIncr(Long qid,String fieldName,int delta) {
        String key= QuestionConstants.HASH_QUESTION_PREFIX +qid;
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
        // 点赞计数加一，防止直接操作mysql
        hashOps.increment(fieldName,delta);
    }

    @Override
    public void view(Long id) {
        this.baseMapper.view(id);
    }

    @Transactional
    @Override
    public void myRemoveByIds(List<Long> asList) {
        // 批量删除帖子
        this.removeByIds(asList);
        // 帖子对应的评论批量删除
        commentService.removeCommentsByQuestionIds(asList);
        // 帖子对应的点赞批量删除
        thumbsService.removeEntitiesByQuestionIds(asList);
    }

    /**
     * 新增帖子，用户的贴子数也要加一
     * @param question
     */
//    @GlobalTransactional
    @Transactional
    @Override
    public void mySave(QuestionEntity question) {
        // 新增帖子
        this.save(question);
        // 用户的贴子数加一
        memberFeignService.addQuestionCount(new MemberEntityTo(question.getCreatorId(),1));
    }
}