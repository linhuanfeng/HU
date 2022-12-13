package com.hu.health.search.config;

import com.alibaba.fastjson.JSON;
import com.hu.health.common.MqConstants;
import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.common.utils.R;
import com.hu.health.search.feign.QuestionFeignService;
import com.hu.health.search.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MqListener {

    @Resource
    private QuestionFeignService questionFeignService;

    @Resource
    private QuestionService questionService;

    @Resource
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * 更新帖子信息（本质就是先标记删除再插入）
     * @param id
     */
    @RabbitListener(queues = MqConstants.QUESTION_UPDATE_QUEUE)
    public void updateQuestion(Long id) {
        scheduledExecutorService.schedule(()->{
            // 线程池异步执行，不然拿不到最新的数据
            R info = questionFeignService.info(id);
            Object question = info.get("question");
            if(question==null){
                log.warn("帖子{}不存在，无法更新",id);
                return;
            }
            QuestionEntity entity = JSON.parseObject(JSON.toJSONString(question), QuestionEntity.class);
            try {
                BulkResponse responses = questionService.insert(Arrays.asList(entity));
            } catch (IOException e) {
                log.error("更新帖子{}到es,失败",id);
            }
            log.info("更新帖子{}到es,成功",entity);
        },10*1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 删除帖子信息（本质就是先标记删除再插入）
     * @param id
     */
    @RabbitListener(queues = MqConstants.QUESTION_DELETE_QUEUE)
    public void deleteQuestion(Long id){
        DeleteResponse deleteResponse = questionService.deleteById(id);
        log.info("删除帖子{}到es,成功",id);
    }
}
