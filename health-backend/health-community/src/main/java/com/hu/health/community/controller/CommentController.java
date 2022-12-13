package com.hu.health.community.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.community.service.QuestionService;
import com.hu.health.community.service.ThumbsService;
import com.hu.health.community.to.CommentTo;
import com.hu.health.community.to.QuestionCommentTo;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.community.entity.CommentEntity;
import com.hu.health.community.service.CommentService;

import javax.annotation.Resource;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
@RestController
@RequestMapping("community/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ThumbsService thumbsService;

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    private QuestionService questionService;

    @Autowired
    ThreadPoolExecutor executor;

    /**
     *
     * @param params
     * @param qid 帖子id
     * @param parent_id 父评论id
     * @return
     */
    @RequestMapping("/list/{qid}/{parent_id}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("qid") Long qid,@PathVariable("parent_id") Long parent_id){
        PageUtils page = commentService.queryPage(params,qid,parent_id);
        return R.ok().put("page", page);
    }

    /**
     * 所有一级评论
     *
     * @param params
     * @param questionId 帖子id
     * @param parent_id 父评论id
     * @return
     */
    @RequestMapping("/listByRedis")
    public R listByRedis(@RequestParam Map<String, Object> params,
                         @RequestParam("qid") Long questionId,
                         @RequestParam("parent_id") Long parent_id){
        // 数量超过100条，只能mysql中获取
        PageUtils page = commentService.queryPageRedisList(params,questionId,parent_id);
        return R.ok().put("page", page);
    }

    /**
     * 一级评论对应的所有二级评论
     *
     * @param params
     * @param questionId 帖子id
     * @param grand_parent_id 一级评论id
     * @return
     */
    @RequestMapping("/listSecComment")
    public R listSecByGrandId(@RequestParam Map<String, Object> params,
                              @RequestParam("qid") Long questionId,
                              @RequestParam("parent_id") Long grand_parent_id){
        // 数量超过100条，只能mysql中获取
        PageUtils page = commentService.listSecByGrandId(params,questionId,grand_parent_id);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CommentEntity comment = commentService.getById(id);

        return R.ok().put("comment", comment);
//        syn
    }

    /**
     * 删除评论
     */
    @RequestMapping("/deleteOne")
    public R deleteOne(@RequestBody CommentTo to) throws ExecutionException, InterruptedException {
        commentService.delEntity(to);
        return R.ok();
    }

    /**
     * 保存评论
     */
    @RequestMapping("/save")
    public R save(@RequestBody CommentEntity comment) throws ExecutionException, InterruptedException {
        commentService.saveEntity(comment);


//        // 异步处理
//        CompletableFuture<Void> commentFuture = CompletableFuture.runAsync(() -> {
//            commentService.save(comment);
//        },executor);
//
//        // 帖子的评论数加一 异步通知
//        final QuestionCommentTo questionCommentTo = new QuestionCommentTo();
//        CompletableFuture.runAsync(()->{
//            questionCommentTo.setId(comment.getQuestionId());
//            questionCommentTo.setCommentCount(1);
//    //        questionService.comment(comment.getQuestionId());
//            rabbitTemplate.convertAndSend("comment-question-exchange",
//                    "comment-question-routingKey",
//                    questionCommentTo);
//        },executor);
//
//        commentFuture.get();
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CommentEntity comment){
		commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		commentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
