package com.hu.health.member.controller;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.member.entity.EvaluationEntity;
import com.hu.health.member.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-22 21:18:14
 */
@RestController
@RequestMapping("ums/evaluation")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("expect:umsexpert:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = evaluationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("expect:umsexpert:info")
    public R info(@PathVariable("id") Long id){
		EvaluationEntity evaluationEntity = evaluationService.getById(id);

        return R.ok().put("umsEvaluation", evaluationEntity);
    }

    /**
     * 新增测评题目
     */
    @RequestMapping("/save")
    // @RequiresPermissions("expect:umsexpert:save")
    public R save(@RequestBody EvaluationEntity entity){
        evaluationService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("expect:umsexpert:update")
    public R update(@RequestBody EvaluationEntity entity){
        evaluationService.updateById(entity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("expect:umsexpert:delete")
    public R delete(@RequestBody Long[] ids){
        evaluationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
