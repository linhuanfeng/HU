package com.hu.health.community.service.impl;

import com.alibaba.fastjson.JSON;
import com.hu.health.common.MqConstants;
import com.hu.health.common.pojo.community.MessageEntity;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.community.feign.MemberFeignService;
import com.hu.health.community.service.QuestionService;
import com.hu.health.community.to.CommentTo;
import com.hu.health.community.to.MemberEntityTo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hu.health.community.dao.CommentDao;
import com.hu.health.community.entity.CommentEntity;
import com.hu.health.community.service.CommentService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;


@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    //    @Autowired
    private QuestionService questionService; // 不然会循环依赖了

    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private MemberFeignService memberFeignService;


    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 使用构造方法依赖注入
     * 自动注入的方式会循环依赖
     *
     * @param questionService
     */
    @Lazy
    public CommentServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * mysql获取评论列表
     *
     * @param params
     * @param qid
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long qid, Long parent_id) {
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params, "like_count", false),
                new QueryWrapper<CommentEntity>().eq("question_id", qid).eq("parent_id", parent_id)
        );

        return new PageUtils(page);
    }

    /**
     * redis list中获取最新的评论列表
     * 所有一级评论
     *
     * @param params
     * @param questionId
     * @param parent_id
     * @return
     */
    @Override
    public PageUtils queryPageRedisList(Map<String, Object> params, Long questionId, Long parent_id) {
        // 分页参数校验
        Object page = params.get("page");
        Object limit = params.get("limit");
        int p = page == null ? 1 : Integer.valueOf((String) page);
        int l = limit == null ? 10 : Integer.valueOf((String) limit);

        // redis list中找出最新的数据
        String key = "list:comment:" + questionId;
        BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key);
        List<String> redisList = listOps.range((p - 1) * l, (p - 1) * l + l);

        // 判断redis数据量是否满足limit大小
        int redisListSize = redisList.size();
        if (redisListSize < l) {
            // 从mysql中获取真正的长度
            IPage<CommentEntity> entityIPage = getBaseMapper().listByQidPid(
                    new Query<CommentEntity>().getPage(params), questionId, parent_id);
            List<CommentEntity> records = entityIPage.getRecords();
            int recordSize = records.size();

            // 比较redis和mysql的数据大小，相等则不需要更新redis(分布式锁+过期续命)
            if (recordSize == redisListSize) {
                log.info("redis和mysql数据一致，不需要更新");
            } else {
                log.info("redis({})和mysql({})数据不一致，开始更新", redisListSize, recordSize);
                // 更新redis，删除帖子对应的所有一级评论缓存（分布式锁）
                deleteCommentList(questionId);
                if(recordSize>0){
                    // 有数量才缓存
                    String[] entityIPageJson = new String[recordSize];
                    for (int i = 0; i < recordSize; i++) {
                        entityIPageJson[i] = JSON.toJSONString(records.get(i));
                    }
                    listOps.rightPushAll(entityIPageJson);
                }
            }
            return new PageUtils(entityIPage);
        } else {
            // 缓存命中直接返回
            return new PageUtils(redisList.stream().map(e -> JSON.parseObject(e, CommentEntity.class)).collect(Collectors.toList()), 0, p, l);
        }
    }

    /**
     * 删除帖子的一级评论缓存（redis list）
     */
    public void deleteCommentList(Long questionId){
        String key = "list:comment:" + questionId;
//        BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key);
        redisTemplate.delete(key);
        log.info("删除key:{}",key);
    }

    @Override
    public PageUtils listSecByGrandId(Map<String, Object> params, Long questionId, Long grand_parent_id) {
        // 分页参数校验
        Object page = params.get("page");
        Object limit = params.get("limit");
        int p = page == null ? 1 : Integer.valueOf((String) page);
        int l = limit == null ? 10 : Integer.valueOf((String) limit);

        IPage<CommentEntity> entityIPage = getBaseMapper().listSecByGrandId(
                new Query<CommentEntity>().getPage(params), questionId, grand_parent_id);
        return new PageUtils(entityIPage);
    }

    /**
     * redis list和zset中获取最新的评论列表
     *
     * @param params
     * @param questionId
     * @return
     */
    @Override
    @Deprecated
    public PageUtils queryPageHotAndLatest(Map<String, Object> params, Long questionId) {
        Object page = params.get("page");
        Object limit = params.get("limit");
        int p = page == null ? 1 : Integer.valueOf((String) page);
        int l = limit == null ? 10 : Integer.valueOf((String) limit);

        String key = "zset:" + questionId + ":comment";

        BoundZSetOperations<String, String> zSetOps = redisTemplate.boundZSetOps(key);

        // 一、获取评论列表
        // 1、从zset中得到点赞数多的评论列表
        Set<String> set;
        try {
            set = zSetOps.reverseRange((p - 1) * l, (p - 1) * l + l);
        } catch (Exception e) {
            set = new HashSet<>();
        }

        String key2 = "list:" + questionId + ":comment:latest";
        // 2、从list得到最新的评论列表
        BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key2);
        List<String> list = listOps.range((p - 1) * l, (p - 1) * l + l);

        // 合并
        for (String s : list) {
            set.add(s);
        }

        String hashKey = "hash:" + questionId + ":comment";
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(hashKey);
        // hash得到对象
        List<String> objects = null;
        try {
            objects = hashOps.multiGet(set);
        } catch (Exception e) {
            objects = new ArrayList<>(set.size());
            log.warn("hash找不到{}对应评论实体", set);
            // 转从mysql中查对应的实体
            List<CommentEntity> commentEntities = listByIds(set);
            Map<String, String> map = new HashMap<>();
            for (CommentEntity comment : commentEntities) {
                String commentJson = JSON.toJSONString(comment);
                map.put(String.valueOf(comment.getId()), commentJson);
                objects.add(commentJson);
            }
            hashOps.putAll(map);
        }

        List<CommentEntity> collect = objects.stream().map(e -> JSON.parseObject(e, CommentEntity.class)).collect(Collectors.toList());
        if (collect.size() < l) {
            // 将数据库的数据导入redis中
            log.warn("redis得到的评论size={}，小于{}", collect.size(), l);
            // 转从mysql中获取
            PageUtils pageUtils = queryPage(params, questionId, 0l);
            List<?> list1 = pageUtils.getList();
            int size1 = list1.size();
            if (size1 <= 0) {
                log.warn("mysql没有更多数据了");
                return new PageUtils(collect, 0, p, l);
            }
            String[] commentIds = new String[l];
            Map<String, String> commentMap = new HashMap<>(size1);

            for (int i1 = 0; i1 < size1; i1++) {
                CommentEntity comment = (CommentEntity) list1.get(i1);
                String id = String.valueOf(comment.getId());
                commentIds[i1] = id;
                commentMap.put(id, JSON.toJSONString(comment));
                collect.add(comment);
            }

            listOps.rightPushAll(commentIds);
            hashOps.putAll(commentMap);
        }
        return new PageUtils(collect, 0, p, l);
    }

    /**
     * 尾插入list，如果超过00条，只保留最新的100条
     *
     * @param entity
     */
//    private void pushListRedis(CommentEntity entity) {
//        String key = "list:comment:last:" + entity.getQuestionId();
//        BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key);
//        Long count = listOps.rightPush(JSON.toJSONString(entity));
//        if (count > 10) {
//            for (int i = 0; i < count - 10; i++) {
//                // 删除头部元素，只保留最新的100条评论
////                listOps.leftPop(count - 10); // 为什么报错：redis.clients.jedis.exceptions.JedisDataException: ERR wrong number of arguments for 'lpop' command
//                listOps.leftPop();
//            }
//        }
//    }

    /**
     * 新增评论
     *
     * @param entity
     */
    @GlobalTransactional
    @Transactional
    @Override
    public void saveEntity(CommentEntity entity) {
        // 新增评论
        this.baseMapper.insert(entity);
        if(entity.getGrandParentId()!=0){
            // 一级评论数加一
            getBaseMapper().comment(entity.getGrandParentId());
            // 评论的缓存失效
            deleteCommentList(entity.getQuestionId());
        }
        // 文章评论数加一
        questionService.comment(entity.getQuestionId());
        // 远程用户的评论数加一
        memberFeignService.addCommentCount(new MemberEntityTo(getQuestionCreatorId(entity.getQuestionId()), 1));
        // 推送评论消息
        MessageEntity message = new MessageEntity();
        message.setFromId(entity.getCreatorId());
        message.setTargetId(getQuestionCreatorId(entity.getQuestionId()));
        message.setCreateTime(new Date());
        message.setContent("评论");
        rabbitTemplate.convertAndSend(MqConstants.QUESTION_EXCHANGE,MqConstants.QUESTION_COMMENT_ROUTINGKEY,message);
    }

    /**
     * 删除评论
     *
     * @param to
     */
//    @GlobalTransactional
    @Transactional
    @Override
    public void delEntity(CommentTo to) {
        // 删除评论
        CommentEntity commentEntity = getBaseMapper().selectById(to.getCommentId());
        this.baseMapper.deleteById(to.getCommentId());
        if(commentEntity.getGrandParentId()!=0){
            // 对应一级评论评论数减一
            unComment(commentEntity.getGrandParentId());
        }
        // 文章评论数减一
        questionService.unComment(to.getQuestionId());
        // 远程文章作者的评论数减一
        memberFeignService.subCommentCount(new MemberEntityTo(getQuestionCreatorId(to.getQuestionId()), 1));
        // 评论缓存删除
        deleteCommentList(to.getQuestionId());
    }

    /**
     * 根据帖子id获取评论人的id
     * @param questionId
     * @return
     */
    private Long getQuestionCreatorId(Long questionId) {
        QuestionEntity entity = questionService.getById(questionId);
        Assert.notNull(entity, "帖子:{" + questionId + "}不存在");
        return entity.getCreatorId();
    }

    /**
     * 根据帖子id列表，删除帖子对应所有评论记录
     * @param questionIds
     */
    @Override
    public void removeCommentsByQuestionIds(List<Long> questionIds) {
        this.baseMapper.removeCommentsByQuestionIds(questionIds);
    }

    /**
     * 评论的点赞数加1
     * @param id
     */
    @Override
    public void thumb(Long id) {
        getBaseMapper().thumb(id);
        // redis缓存评论删除
        CommentEntity commentEntity = getById(id);
        deleteCommentList(commentEntity.getQuestionId());
    }

    /**
     * 评论点赞数减1
     * @param id
     */
    @Override
    public void unThumb(Long id) {
        getBaseMapper().unThumb(id);
        // redis缓存评论删除
        CommentEntity commentEntity = getById(id);
        deleteCommentList(commentEntity.getQuestionId());
    }

    @Override
    public void comment(Long id) {
        getBaseMapper().comment(id);
    }

    @Override
    public void unComment(Long id) {
        getBaseMapper().uncomment(id);
    }
}