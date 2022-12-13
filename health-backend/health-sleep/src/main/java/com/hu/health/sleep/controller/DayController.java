package com.hu.health.sleep.controller;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hu.health.sleep.entity.DayEntity;
import com.hu.health.sleep.service.DayService;
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
@RequestMapping("sleep/day")
public class DayController {
    @Autowired
    private DayService dayService;

    //获取本周睡眠平均分
    @RequestMapping("/getWeekScore/{id}")
    public R getWeekScore(@PathVariable Long id){
        Double weekScore= dayService.getWeekScore(id);
        return  R.ok().put("weekScore",weekScore);
    }

    //获取本月睡眠平均分
    @RequestMapping("/getMonthScore/{id}")
    public R getMonthScore(@PathVariable Long id){
        Double monthScore= dayService.getMonthScore(id);
        return  R.ok().put("weekScore",monthScore);
    }
    //获取当日的睡眠分数
    @RequestMapping("/getDayScore/{id}/{data}")
    public R getDayScore(@PathVariable Long id,@PathVariable String data){
        Double dayScore = dayService.getDayScore(id, data);
        return R.ok().put("dayScore",dayScore);
    }

    //获取本月睡眠记录
    @RequestMapping("/getMonthList/{id}")
    public R getMonthList(@PathVariable Long id){
        List<DayEntity> monthkList = dayService.getMonthList(id);
        return R.ok().put("monthList",monthkList);
    }

    //获取本周睡眠记录
    @RequestMapping("/getWeekList/{id}")
    public R getWeekList(@PathVariable Long id){
        List<DayEntity> weekList = dayService.getWeekList(id);
        return R.ok().put("weekList",weekList);
    }

    //获取当日睡眠时长
    @RequestMapping("/getAllDuration/{id}/{data}")
    public R getAllDuration(@PathVariable Long id,@PathVariable String data){
        String duration=dayService.allDuration(id,data);
        return R.ok().put("allduration",duration);
    }


    //获取睡眠持续时间
    @RequestMapping("/getDuration/{id}/{data}")
        public R getDuration(@PathVariable Long id,@PathVariable String data){
        String duration=dayService.duration(id,data);
        return R.ok().put("duration",duration);
        }

    //开始睡眠
    @RequestMapping("/startSleep/{id}/{data}")
    public R startSleep(@PathVariable Long id,@PathVariable String data){
        dayService.startSleep(id,data);
        return R.ok();
    }

    //结束睡眠
    @RequestMapping("/endSleep/{id}/{data}")
    public R endSleep(@PathVariable Long id,@PathVariable String data){
        dayService.endSleep(id,data);
        return R.ok();
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("expect:day:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dayService.queryPage(params);
        System.out.println(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("expect:day:info")
    public R info(@PathVariable("id") Long id){
		DayEntity day = dayService.getById(id);
System.out.println(day);
        return R.ok().put("day", day);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("expect:day:save")
    public R save(@RequestBody DayEntity day){
		dayService.save(day);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("expect:day:update")
    public R update(@RequestBody DayEntity day){
		dayService.updateById(day);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("expect:day:delete")
    public R delete(@RequestBody Long[] ids){
		dayService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
