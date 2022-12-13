package com.hu.health.selfdiagonsis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.selfdiagonsis.entity.SelfdiagonsisSymptomEntity;
import com.hu.health.selfdiagonsis.service.SelfdiagonsisSymptomService;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.sport.controller.R;
//import com.hu.health.common.utils.PageUtils;



/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-19 00:28:15
 */
@RestController
@RequestMapping("selfdiagonsis/selfdiagonsissymptom")
public class SelfdiagonsisSymptomController {
    @Autowired
    private SelfdiagonsisSymptomService selfdiagonsisSymptomService;


    //根据id树形查询
    @RequestMapping("/getByIdTree/{catId}")
    public R getByIdTree(@PathVariable("catId") Long catId){
      SelfdiagonsisSymptomEntity selfdiagonsisSymptom=selfdiagonsisSymptomService.getByIdTree(catId);
        return R.ok().put("data",selfdiagonsisSymptom);
    }

    //查询树形结构有效列表
    @RequestMapping("/listtreeOnShell")
    public R listtreeOnShell(){
        List<SelfdiagonsisSymptomEntity> entityList=selfdiagonsisSymptomService.listWithTreeOnShell();
        return R.ok().put("data",entityList);
    }

    //查询树形结构列表
    @RequestMapping("/listtree")
    public R listtree(){
        List<SelfdiagonsisSymptomEntity> entityList=selfdiagonsisSymptomService.listWithTree();
        return R.ok().put("data",entityList);
    }


     //上架数据
    @RequestMapping("/OntheShell/{catId}")
    public R OntheShell(@PathVariable("catId") Long catId){
        selfdiagonsisSymptomService.Ontheshelf(catId);
        return R.ok();
    }

    //下架数据
    @RequestMapping("/OfftheShell/{catId}")
    public R OfftheShell(@PathVariable("catId") Long catId){
        selfdiagonsisSymptomService.Offtheshelf(catId);
        return R.ok();
    }


    //批量上架数据
    @RequestMapping("/OntheShell")
    public R OntheShells(@RequestBody Long catId[]){
       selfdiagonsisSymptomService.Ontheshelfs(catId);
       return R.ok();
    }

    //批量下架数据
    @RequestMapping("/OfftheShell")
    public R OfftheShells(@RequestBody Long catId[]){
        selfdiagonsisSymptomService.Offtheshelfs(catId);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("selfdiagonsis:selfdiagonsissymptom:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = selfdiagonsisSymptomService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    // @RequiresPermissions("selfdiagonsis:selfdiagonsissymptom:info")
    public R info(@PathVariable("catId") Long catId){
		SelfdiagonsisSymptomEntity selfdiagonsisSymptom = selfdiagonsisSymptomService.getById(catId);


        return R.ok().put("selfdiagonsisSymptom", selfdiagonsisSymptom);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("selfdiagonsis:selfdiagonsissymptom:save")
    public R save(@RequestBody SelfdiagonsisSymptomEntity selfdiagonsisSymptom){
		selfdiagonsisSymptomService.save(selfdiagonsisSymptom);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("selfdiagonsis:selfdiagonsissymptom:update")
    public R update(@RequestBody SelfdiagonsisSymptomEntity selfdiagonsisSymptom){
		selfdiagonsisSymptomService.updateById(selfdiagonsisSymptom);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("selfdiagonsis:selfdiagonsissymptom:delete")
    public R delete(@RequestBody Long[] catIds){
		selfdiagonsisSymptomService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
