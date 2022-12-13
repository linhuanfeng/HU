package com.hu.health.community.controller;

import java.util.Arrays;
import java.util.Map;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.community.entity.ThumbsEntity;
import com.hu.health.community.service.ThumbsService;

import javax.annotation.Resource;


/**
 *
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 22:10:15
 */
@RestController
@RequestMapping("community/thumb")
public class ThumbsController {
    @Autowired
    private ThumbsService thumbsService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = thumbsService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ThumbsEntity thumbs = thumbsService.getById(id);
        return R.ok().put("thumbs", thumbs);
    }

    /**
     * 点赞评论
     */
    @RequestMapping("/save")
    public R save(@RequestBody ThumbsEntity thumbs){
//		thumbsService.saveEntity(thumbs);
        thumbsService.saveEntityRedis(thumbs);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ThumbsEntity thumbs){
        thumbsService.updateById(thumbs);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        thumbsService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 取消点赞帖子
     */
    @RequestMapping("/cancel")
    public R cancel(@RequestBody ThumbsEntity entity){
        thumbsService.removeEntity(entity);
        return R.ok();
    }
}
