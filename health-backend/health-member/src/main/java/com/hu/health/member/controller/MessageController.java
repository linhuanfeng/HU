package com.hu.health.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hu.health.common.pojo.community.MessageEntity;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.member.service.MessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


/**
 *
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 22:10:15
 */
@RestController
@RequestMapping("ums/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 查询消息
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageService.queryPage(params);
        return R.ok().put("page", page);
    }
//
//
//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Long id){
//        ThumbsEntity thumbs = messageService.getById(id);
//        return R.ok().put("thumbs", thumbs);
//    }
//
//    /**
//     * 点赞评论
//     */
//    @RequestMapping("/save")
//    public R save(@RequestBody ThumbsEntity thumbs){
////		thumbsService.saveEntity(thumbs);
//        thumbsService.saveEntityRedis(thumbs);
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    public R update(@RequestBody ThumbsEntity thumbs){
//        thumbsService.updateById(thumbs);
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Long[] ids){
//        thumbsService.removeByIds(Arrays.asList(ids));
//        return R.ok();
//    }
//
//    /**
//     * 取消点赞帖子
//     */
//    @RequestMapping("/cancel")
//    public R cancel(@RequestBody ThumbsEntity entity){
//        thumbsService.removeEntity(entity);
//        return R.ok();
//    }
}
