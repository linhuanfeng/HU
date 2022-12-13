package com.hu.health.diet.controller;

import java.util.Arrays;
import java.util.Map;

//import com.baomidou.mybatisplus.extension.api.R;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.diet.entity.DietMenuEntity;
import com.hu.health.diet.entity.Food;
import com.hu.health.diet.entity.HighAndLow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.diet.entity.DayEntity;
import com.hu.health.diet.service.DayService;
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
@RequestMapping("diet/day")
public class
DayController {

    @Autowired
    private DayService dayService;





    //获取日应获取的卡路里
    @RequestMapping("/getShouldCartion/{id}")
    public R getCation(@PathVariable Long id){
        HighAndLow exc = dayService.getCatrion(id);
        return R.ok().put("shouleCartion",exc);
    }

    //获取日应获取的脂肪
    @RequestMapping("/getShouldFat/{id}")
    public R getFat(@PathVariable Long id){
        HighAndLow exc = dayService.getFat(id);
        return R.ok().put("shouleFat",exc);
    }

    //获取日应获取的蛋白质
    @RequestMapping("/getShouldProteinProportion/{id}")
    public R getPro(@PathVariable Long id){
        HighAndLow exc = dayService.getProportion(id);
        return R.ok().put("shouleProteinProportion",exc);
    }

    //获取日应获取的热量
    @RequestMapping("/getShouldHot/{id}")
    public R getHot(@PathVariable Long id){
        HighAndLow exc = dayService.getExc(id);
        return R.ok().put("shouleHot",exc);
    }


    //获取蛋白质占比
    @RequestMapping("/getProteinProportion/{id}/{data}")
    public R getProteinProportion(@PathVariable Long id,@PathVariable String data){
        double sum=dayService.getProteinProportion(id,data);
        return R.ok().put("ProteinProportion",sum);
    }

    //获取脂肪占比
    @RequestMapping("/getFatProportion/{id}/{data}")
    public R getFatProportion(@PathVariable Long id,@PathVariable String data){
        double sum=dayService.getFatProportion(id,data);
        return R.ok().put("ProteinProportion",sum);
    }

    //获取碳水占比
    @RequestMapping("/getCarbonWaterProportion/{id}/{data}")
    public R getCarbonWaterProportion(@PathVariable Long id,@PathVariable String data){
        double sum=dayService.getCatrionWaterProportion(id,data);
        return R.ok().put("ProteinProportion",sum);
    }



    //获取热量
    @RequestMapping("/getCalorie/{id}/{data}")
    public R getCalorie(@PathVariable Long id,@PathVariable String data){
        double sum=dayService.getCalorie(id,data);
       return R.ok().put("Calorie",sum);

    }

    //获取蛋白质
    @RequestMapping("/getProtein/{id}/{data}")
    public R getProtein(@PathVariable Long id,@PathVariable String data){
        double sum=dayService.getProtein(id,data);
        return R.ok().put("Calorie",sum);
    }

    //获取脂肪
    @RequestMapping("/getFat/{id}/{data}")
    public R getFat(@PathVariable Long id,@PathVariable String data){
        double sum=dayService.getFat(id,data);
        return R.ok().put("Calorie",sum);
    }

    //获取蛋白质
    @RequestMapping("/getCarbonWater/{id}/{data}")
    public R geteCarbonWater(@PathVariable Long id,@PathVariable String data){
        double sum=dayService.getCarbonWater(id,data);
        return R.ok().put("Calorie",sum);
    }


    //获取所有餐
    @RequestMapping("/getAllMean/{id}/{data}")
    public R getAllMean(@PathVariable Long id,@PathVariable String data){

        Map<DietMenuEntity, String> allMeal = dayService.getAllMeal(id, data);
        return R.ok().put("data",allMeal);
    }

    //添加早餐
    @RequestMapping("/addBreakfast/{id}/{data}")
    public R addBreakfast(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.AddBreakfast(id,data,food);
        return R.ok();
    }

    //添加午餐
    @RequestMapping("/addLunch/{id}/{data}")
    public R addLunch(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.AddLunch(id,data,food);
        return R.ok();
    }

    //添加晚餐
    @RequestMapping("/addDinner/{id}/{data}")
    public R addDinner(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.AddDinner(id,data,food);
        return R.ok();
    }

    //加餐
    @RequestMapping("/addAdds/{id}/{data}")
    public R addAdds(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.AddAdds(id,data,food);
        return R.ok();
    }

    //删除早餐
    @RequestMapping("/decBreakfast/{id}/{data}")
    public R decBreakfast(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.DecBreakfast(id,data,food);
        return R.ok();
    }

    //删除午餐
    @RequestMapping("/decLunch/{id}/{data}")
    public R decLunch(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.DecLunch(id,data,food);
        return R.ok();
    }

    //删除晚餐
    @RequestMapping("/decDinner/{id}/{data}")
    public R decDinner(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.DecDinner(id,data,food);
        return R.ok();
    }

    //删除加餐
    @RequestMapping("/decAdds/{id}/{data}")
    public R decAdds(@RequestBody Food food,@PathVariable Long id,@PathVariable String data){
        System.out.println(food);
        dayService.DecAdds(id,data,food);
        return R.ok();
    }

    //获取早餐
    @RequestMapping("/showBreakfast/{id}/{data}")
    public R showBreakfast(@PathVariable Long id,@PathVariable String data){
        Map<DietMenuEntity,String > dietMenuEntities = dayService.showBreakfast(id, data);
        return R.ok().put("data",dietMenuEntities);
    }
    //获取午餐
    @RequestMapping("/showLunch/{id}/{data}")
    public R showLunch(@PathVariable Long id,@PathVariable String data){
        Map<DietMenuEntity,String> dietMenuEntities = dayService.showLunch(id, data);
        return R.ok().put("data",dietMenuEntities);
    }
    //获取晚餐
    @RequestMapping("/showDinner/{id}/{data}")
    public R showDinner(@PathVariable Long id,@PathVariable String data){
        Map<DietMenuEntity,String> dietMenuEntities = dayService.showDinner(id, data);
        return R.ok().put("data",dietMenuEntities);
    }
    //获取加餐
    @RequestMapping("/showAdds/{id}/{data}")
    public R showAdds(@PathVariable Long id,@PathVariable String data){
        Map<DietMenuEntity,String> dietMenuEntities = dayService.showAdds(id, data);
        return R.ok().put("data",dietMenuEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("diet:day:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dayService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("diet:day:info")
    public R info(@PathVariable("id") Long id){
		DayEntity day = dayService.getById(id);

        return R.ok().put("day", day);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("diet:day:save")
    public R save(@RequestBody DayEntity day){
		dayService.save(day);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("diet:day:update")
    public R update(@RequestBody DayEntity day){
		dayService.updateById(day);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("diet:day:delete")
    public R delete(@RequestBody Long[] ids){
		dayService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
