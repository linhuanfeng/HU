package com.hu.health.community.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.hu.health.common.pojo.SearchParam;
import com.hu.health.common.pojo.community.QuestionEntity;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.community.feign.ElasticSearchFeignService;
import org.springframework.web.bind.annotation.*;

import com.hu.health.community.service.QuestionService;

import javax.annotation.Resource;

/**
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:35
 */
@RestController
@RequestMapping("community/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private ElasticSearchFeignService elasticSearchFeignService;

    @RequestMapping("/view")
    public R view(@RequestParam("id") Long id){
        questionService.view(id);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // 这里为什么返回数据为空乐
//    @Cacheable(value = "article",keyGenerator = "articleGenerator")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = questionService.queryPage(params);

        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listByEs")
    // 这里为什么返回数据为空乐
//    @Cacheable(value = "article",keyGenerator = "articleGenerator")
    public R listByEs(@RequestParam Map<String, Object> params) throws ExecutionException, InterruptedException {
        R r = elasticSearchFeignService.search(SearchParam.parse(params));

        return r;
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		QuestionEntity question = questionService.getById(id);

        return R.ok().put("question", question);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody QuestionEntity question){
		questionService.mySave(question);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody QuestionEntity question){
		questionService.updateById(question); // 字段不为空就会更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R deleteEntity(@RequestBody Long[] ids){
        // 批量删除帖子
        questionService.myRemoveByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 评论计数减一
     */
    @RequestMapping("/unComment/{id}")
    public R deleteOne(@PathVariable Long id){
        questionService.unComment(id);
        return R.ok();
    }
}
