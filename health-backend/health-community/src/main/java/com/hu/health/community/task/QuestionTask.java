package com.hu.health.community.task;

import com.hu.health.common.MqConstants;
import com.hu.health.common.pojo.community.MessageEntity;
import com.hu.health.community.entity.ThumbsEntity;
import com.hu.health.community.service.CommentService;
import com.hu.health.community.service.QuestionService;
import com.hu.health.community.service.ThumbsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/*
1、*：通配符，表示该字段可以接收任意值。

2、？ ：表示不确定的值，或不关心它为何值，仅在日期和星期中使用，当其中一个设置了条件时，另外一个用"?" 来表示"任何值"。

3、,：表示多个值，附加一个生效的值。

4、-：表示一个指定的范围

5、/：指定一个值的增量值。例n/m表示从n开始，每次增加m

6、L：用在日期表示当月的最后一天，用在星期"L"单独使用时就等于"7"或"SAT"，如果和数字联合使用表示该月最后一个星期X。例如，"0L"表示该月最后一个星期日。

7、W：指定离给定日期最近的工作日（周一到周五），可以用"LW"表示该月最后一个工作日。例如，"10W"表示这个月离10号最近的工作日

8、C：表示和calendar联系后计算过的值。例如：用在日期中，"5C"表示该月第5天或之后包括calendar的第一天；用在星期中，"5C"表示这周四或之后包括calendar的第 一天。

9、#：表示该月第几个星期X。例6#3表示该月第三个周五。
 */
@Component
@Slf4j
public class QuestionTask {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private ThumbsService thumbsService;

    @Resource
    private CommentService commentService;

    @Resource
    private QuestionService questionService;

    /**
     * 将帖子的评论和点赞计数同步到mysql中
     *
     * cron表达式由6-7个时间域组成，每个时间域之间用空格隔开。
     * 	格式：
     * 	<秒> <分> <时> <日期> <月> <星期几>
     *
     * ?出现在日期或星期，表示不确定
     *
     */
//    @Scheduled(cron = "0/20 * * * * ?") // 每隔5秒触发
//    public void questionCountToMysql(){
//        String keys = QuestionConstants.HASH_QUESTION_PREFIX+"*";
//        Set<String> set = redisTemplate.keys(keys);
//
//        for (String key : set) {
//            BoundHashOperations<String, String, Object> hashOps = redisTemplate.boundHashOps(key);
//
//            Map<String, Object> map = hashOps.entries();
//
//            Object view_count = map.get("view_count");
//            if(view_count!=null){
//                Integer count=(Integer) view_count;
//            }
//
//            Object comment_count = map.get("comment_count");
//            if (comment_count != null) {
//                Integer count = (Integer) comment_count;
//                if (count != 0) {
//                    long questionId = Long.parseLong(key.substring(key.lastIndexOf(":") + 1));
////                    questionService.comment(questionId, count);
//                    // 反向减少
////                    hashOps.increment("comment_count", count * -1);
//                }
//            }
//
//            Object like_count = map.get("like_count");
//            if (like_count != null) {
//                Integer count = (Integer) like_count;
//                if (count != 0) {
//                    long questionId = Long.parseLong(key.substring(key.lastIndexOf(":") + 1));
////                    questionService.thumb(questionId, count);
////                    hashOps.increment("like_count", count * -1);
//                }
//            }
//        }
//    }

    /**
     * 点赞数据（帖子和评论）同步到mysql
     */
    @Scheduled(cron = "0/20 * * * * ?") // 每隔5秒触发
    public void thumbToMysql(){
        // 获取所有set,前缀为 set:thumb:*
        Set<String> set = redisTemplate.keys("set:thumb:*");
        for (String s : set) {
            // 给帖子点赞，key  set:thumb:entityId:type  value  memberId
            String[] split = s.split(":");
            // 目标实体id
            long entityId = Long.valueOf(split[2]);
            // 类型 0表示帖子，1表示评论
            Integer type = Integer.valueOf(split[3]);

            BoundSetOperations<String, String> setOps1 = redisTemplate.boundSetOps(s);
            // 点赞的用户数（点赞数）
            long size = setOps1.size();
            // 点赞的用户id
            Set<String> ids = setOps1.members();
            // 删除redis数据
            redisTemplate.delete(s);
            // 保存点赞记录（帖子和评论的点赞数据在同个表）
            for (String id : ids) {
                ThumbsEntity thumbsEntity = new ThumbsEntity();
                thumbsEntity.setMemberId(Long.parseLong(id));
                thumbsEntity.setType(type);
                thumbsEntity.setEntityId(entityId);
                try {
                    thumbsService.save(thumbsEntity);
                }catch (Exception e){
                   log.warn("保存点赞记录出错：{}",e.getMessage());
                   return;
                }
            }

            if(type==ThumbsEntity.EntityType.questionType.getKey()){
                // 帖子的点赞数增加
                questionService.thumb(entityId, (int) size);
            } else if (type==ThumbsEntity.EntityType.commentType.getKey()) {
                // 评论的点赞数增加
                commentService.thumb(entityId);
            }
            // 推送评论消息
            MessageEntity message = new MessageEntity();
//            message.setFromId(entity.getCreatorId());
//            message.setToId(getQuestionCreatorId(entity.getQuestionId()));
            message.setCreateTime(new Date());
            message.setContent("点赞");
            rabbitTemplate.convertAndSend(MqConstants.QUESTION_EXCHANGE,MqConstants.QUESTION_COMMENT_ROUTINGKEY,message);

        }
    }
}
