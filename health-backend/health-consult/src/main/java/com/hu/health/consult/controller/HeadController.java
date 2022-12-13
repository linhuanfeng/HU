package com.hu.health.consult.controller;

//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.sport.controller.R;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.consult.entity.HeadEntity;
import com.hu.health.consult.service.HeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("consult/head")
public class HeadController {
    @Autowired
    private HeadService headService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("sport:music:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = headService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("sport:music:info")
    public R info(@PathVariable("id") Long id){
        HeadEntity headEntity = headService.getById(id);

        return R.ok().put("head", headEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("sport:music:save")
    public R save(@RequestBody HeadEntity headEntity){
        headService.save(headEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("sport:music:update")
    public R update(@RequestBody HeadEntity headEntity){
        headService.updateById(headEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("sport:music:delete")
    public R delete(@RequestBody Long[] ids){
        headService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
