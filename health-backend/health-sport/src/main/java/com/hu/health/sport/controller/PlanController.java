package com.hu.health.sport.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.sport.entity.PlanEntity;
import com.hu.health.sport.service.PlanService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;



/**
 * 运动计划
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
@RestController
@RequestMapping("sport/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("sport:plan:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = planService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{planId}")
    // @RequiresPermissions("sport:plan:info")
    public R info(@PathVariable("planId") Long planId){
		PlanEntity plan = planService.getById(planId);

        return R.ok().put("plan", plan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("sport:plan:save")
    public R save(@RequestBody PlanEntity plan){
		planService.save(plan);

        return R.ok();
    }

    /**
     * 根据计划id来修改
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("sport:plan:update")
    public R update(@RequestBody PlanEntity plan){
		planService.updateById(plan);

        return R.ok();
    }

    /**
     * 根据用户id来修改
     * 修改
     */
    @RequestMapping("/update-uid")
    // @RequiresPermissions("sport:plan:update")
    public R updateByUId(@RequestBody PlanEntity plan){
        planService.updateByUId(plan);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("sport:plan:delete")
    public R delete(@RequestBody Long[] planIds){
		planService.removeByIds(Arrays.asList(planIds));

        return R.ok();
    }

}
