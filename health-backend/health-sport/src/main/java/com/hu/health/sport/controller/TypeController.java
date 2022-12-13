package com.hu.health.sport.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.sport.entity.TypeEntity;
import com.hu.health.sport.service.TypeService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;



/**
 * 运动类型
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:31
 */
@RestController
@RequestMapping("sport/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @PreAuthorize("hasAuthority('user.add')")
    // @RequiresPermissions("sport:type:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = typeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("sport:type:info")
    public R info(@PathVariable("id") Long id){
		TypeEntity type = typeService.getById(id);

        return R.ok().put("type", type);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("sport:type:save")
    public R save(@RequestBody TypeEntity type){
		typeService.save(type);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("sport:type:update")
    public R update(@RequestBody TypeEntity type){
		typeService.updateById(type);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("sport:type:delete")
    public R delete(@RequestBody Long[] ids){
		typeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
