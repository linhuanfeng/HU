package com.hu.health.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.hu.health.member.entity.ExpertEntity;
import com.hu.health.member.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;



/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-22 21:18:14
 */
@RestController
@RequestMapping("ums/expert")
public class ExpertController {
    @Autowired
    private ExpertService expertService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("expect:umsexpert:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expertService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("expect:umsexpert:info")
    public R info(@PathVariable("id") Long id){
		ExpertEntity umsExpert = expertService.getById(id);

        return R.ok().put("umsExpert", umsExpert);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("expect:umsexpert:save")
    public R save(@RequestBody ExpertEntity umsExpert){
		expertService.save(umsExpert);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("expect:umsexpert:update")
    public R update(@RequestBody ExpertEntity umsExpert){
		expertService.updateById(umsExpert);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("expect:umsexpert:delete")
    public R delete(@RequestBody Long[] ids){
		expertService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
