package com.hu.health.member.controller;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.member.entity.ReportEntity;
import com.hu.health.member.service.ReportService;
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
@RequestMapping("ums/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("expect:umsexpert:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = reportService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取健康报告
     */
    @RequestMapping("/result/{score}")
    // @RequiresPermissions("expect:umsexpert:info")
    public R info(@PathVariable("score") Integer score){
        ReportEntity reportEntity = reportService.result(score);

        return R.ok().put("umsReport", reportEntity);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("expect:umsexpert:info")
    public R info(@PathVariable("id") Long id){
		ReportEntity reportEntity = reportService.getById(id);

        return R.ok().put("umsReport", reportEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("expect:umsexpert:save")
    public R save(@RequestBody ReportEntity entity){
		reportService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("expect:umsexpert:update")
    public R update(@RequestBody ReportEntity reportEntity){
		reportService.updateById(reportEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("expect:umsexpert:delete")
    public R delete(@RequestBody Long[] ids){
		reportService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
