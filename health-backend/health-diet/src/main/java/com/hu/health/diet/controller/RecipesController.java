package com.hu.health.diet.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.diet.entity.DietMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.diet.entity.RecipesEntity;
import com.hu.health.diet.service.RecipesService;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.sport.controller.R;



/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-17 06:09:21
 */
@RestController
@RequestMapping("diet/recipes")
public class RecipesController {
    @Autowired
    private RecipesService recipesService;

    @RequestMapping("/getByIdTree")
    public R getByIdTree(@PathVariable("catId") Long catId){
        RecipesEntity selfdiagonsisSymptom=recipesService.getByIdTree(catId);
        return R.ok().put("data",selfdiagonsisSymptom);
    }

@RequestMapping("/OnShellList")
    public R OnShellList(){
        List<RecipesEntity> recipesEntities=recipesService.OnShellList();
        return R.ok().put("data",recipesEntities);
    }

    @RequestMapping("/OfftheShell")
    public R OfftheShell(@RequestBody Long cat_id[]){
        recipesService.OfftheShell(cat_id);
        return R.ok();
    }


    @RequestMapping("/OntheShell")
    public R OntheShell(@RequestBody Long cat_id[]){
        recipesService.OntheShells(cat_id);
        return R.ok();
    }

    @RequestMapping("/listtree")
    public R listtree(){
        List<RecipesEntity> entityList=recipesService.listWithTree();
        return R.ok().put("data",entityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("diet:recipes:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = recipesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("diet:recipes:info")
    public R info(@PathVariable("id") Long id){
		RecipesEntity recipes = recipesService.getById(id);

        return R.ok().put("recipes", recipes);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("diet:recipes:save")
    public R save(@RequestBody RecipesEntity recipes){
		recipesService.save(recipes);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("diet:recipes:update")
    public R update(@RequestBody RecipesEntity recipes){
		recipesService.updateById(recipes);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("diet:recipes:delete")
    public R delete(@RequestBody Long[] ids){
		recipesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
