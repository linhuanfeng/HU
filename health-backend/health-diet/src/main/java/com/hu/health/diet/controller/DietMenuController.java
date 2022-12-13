package com.hu.health.diet.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.diet.entity.DietMenuEntity;
import com.hu.health.diet.service.DietMenuService;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.sport.controller.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-02-14 20:50:23
 */
@RefreshScope
@RestController
@RequestMapping("diet/dietmenu")
public class DietMenuController {
    @Autowired
    private DietMenuService dietMenuService;
    //根据id树形查询
    @RequestMapping("/getByIdTree/{catId}")
    public R getByIdTree(@PathVariable("catId") Long catId){
        DietMenuEntity selfdiagonsisSymptom=dietMenuService.getByIdTree(catId);
        return R.ok().put("data",selfdiagonsisSymptom);
    }

    @RequestMapping("/OnShellList")
    public R OnShellList(){
        List<DietMenuEntity> dietMenuEntities=dietMenuService.OnShelllist();
        return R.ok().put("data",dietMenuEntities);
    }
    //测试

    @RequestMapping("/OnTheShell")
    public R OnTheShell(@RequestBody Long cat_id[]){
        dietMenuService.OnTheShell(cat_id);
        return R.ok();
    }

    @RequestMapping("/OffTheShell")
    public R OffTheShell(@RequestBody Long cat_id[]){
        dietMenuService.OffTheShell(cat_id);
        return R.ok();
    }

    //查询树形结构列表
    @RequestMapping("/listtree")
    public R listtree(){
        List<DietMenuEntity> entityList=dietMenuService.listWithTree();
    return R.ok().put("data",entityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dietMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")

    public R info(@PathVariable("catId") Long catId){
		DietMenuEntity dietMenu = dietMenuService.getById(catId);

        return R.ok().put("dietMenu", dietMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")

    public R save(@RequestBody DietMenuEntity dietMenu){
		dietMenuService.save(dietMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")

    public R update(@RequestBody DietMenuEntity dietMenu){
		dietMenuService.updateById(dietMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")

    public R delete(@RequestBody Long[] catIds){
		dietMenuService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
