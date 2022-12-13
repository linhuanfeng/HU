package com.hu.health.community.controller;

import java.util.Arrays;
import java.util.Map;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.community.entity.AreaEntity;
import com.hu.health.community.service.AreaService;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.sport.controller.R;



/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:36
 */
@RestController
@RequestMapping("community/area")
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("community:area:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = areaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("community:area:info")
    public R info(@PathVariable("id") Long id){
		AreaEntity area = areaService.getById(id);

        return R.ok().put("area", area);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("community:area:save")
    public R save(@RequestBody AreaEntity area){
		areaService.save(area);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("community:area:update")
    public R update(@RequestBody AreaEntity area){
		areaService.updateById(area);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("community:area:delete")
    public R delete(@RequestBody Long[] ids){
		areaService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
