package com.hu.health.member.controller;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.member.service.EvaluationResService;
import com.hu.health.member.entity.EvaluationResEntity;
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
@RequestMapping("ums/evalres")
public class EvaluationResController {
    @Autowired
    private EvaluationResService evaluationResService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("expect:umsexpert:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = evaluationResService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("expect:umsexpert:info")
    public R info(@PathVariable("id") Long id){
		EvaluationResEntity evaluationResEntity = evaluationResService.getById(id);

        return R.ok().put("umsEvalRes", evaluationResEntity);
    }

    /**
     * 新增测评题目
     */
    @RequestMapping("/save")
    // @RequiresPermissions("expect:umsexpert:save")
    public R save(@RequestBody EvaluationResEntity entity){
        evaluationResService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("expect:umsexpert:update")
    public R update(@RequestBody EvaluationResEntity entity){
        evaluationResService.updateById(entity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("expect:umsexpert:delete")
    public R delete(@RequestBody Long[] ids){
        evaluationResService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
