package com.hu.health.consult.controller;

//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.sport.controller.R;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.consult.dto.AskEntityDto;
import com.hu.health.consult.entity.AskEntity;
import com.hu.health.consult.service.AskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("consult/ask")
public class AskController {
    @Autowired
    private AskService askService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("sport:music:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = askService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("sport:music:info")
    public R info(@PathVariable("id") Long id){
        AskEntity askEntity = askService.getById(id);

        return R.ok().put("ask", askEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("sport:music:save")
    public R save(@RequestBody AskEntityDto askEntityDto){
        askService.save(new AskEntity(askEntityDto));

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("sport:music:update")
    public R update(@RequestBody AskEntity askEntity){
        askService.updateById(askEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("sport:music:delete")
    public R delete(@RequestBody Long[] ids){
        askService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
