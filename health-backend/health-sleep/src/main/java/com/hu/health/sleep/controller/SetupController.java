package com.hu.health.sleep.controller;

import java.util.Arrays;
import java.util.Map;

import com.hu.health.sleep.entity.SetupEntity;
import com.hu.health.sleep.service.SetupService;
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
 * @date 2022-02-27 15:22:21
 */
@RestController
@RequestMapping("sleep/setup")
public class SetupController {
    @Autowired
    private SetupService setupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("expect:setup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = setupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    // @RequiresPermissions("expect:setup:info")
    public R info(@PathVariable("userId") Long userId){
		SetupEntity setup = setupService.getById(userId);

        return R.ok().put("setup", setup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("expect:setup:save")
    public R save(@RequestBody SetupEntity setup){
		setupService.save(setup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("expect:setup:update")
    public R update(@RequestBody SetupEntity setup){
		setupService.updateById(setup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("expect:setup:delete")
    public R delete(@RequestBody Long[] userIds){
		setupService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
