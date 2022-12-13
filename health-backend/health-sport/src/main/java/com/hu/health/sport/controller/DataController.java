package com.hu.health.sport.controller;

import java.util.Arrays;
import java.util.Map;

import com.hu.health.common.utils.R;
import com.hu.health.sport.feign.MemberFeignService;
import com.hu.health.sport.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hu.health.sport.entity.DataEntity;
import com.hu.health.sport.service.DataService;
import com.hu.health.common.utils.PageUtils;



/**
 * 运动数据
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-14 19:08:32
 */
@RestController
@RequestMapping("sport/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @Autowired
    MemberFeignService memberFeignService;


    /**
     * 获取好友排行，调用接口
     * @return
     */
    @GetMapping("/friendRank/{uid}")
//    @PreAuthorize("hasAuthority('user.list-my')")
    public R friendRank(@PathVariable("uid") Long uid){
//        int i=1/0;
        R r= memberFeignService.info(uid);
        return r;
    }

    /**
     * 获取今日运动记录s
     * @param day 0表示今天，1表示昨天，2表示前天
     * @return
     */
    @GetMapping("/detail/{uid}/{day}")
    public R detail(@RequestParam Map<String, Object> params,
                    @PathVariable("uid") Long uid,
                    @PathVariable(value = "day") Integer day){
        PageUtils detailsVo= dataService.detail(params,uid,day);
        return R.ok().put("page",detailsVo);
    }

    /**
     * 获取本周运动记录
     * @param week 0表示今天，1表示昨天，2表示前天
     * @return
     */
    @GetMapping("/detail-week/{uid}/{week}")
    public R detailWeek(@RequestParam Map<String, Object> params,
                    @PathVariable("uid") Long uid,
                    @PathVariable(value = "week") Integer week){
        PageUtils detailsVo= dataService.detailWeek(params,uid,week);
        return R.ok().put("page",detailsVo);
    }

    /**
     * 获取本月运动记录
     * @param month 0表示今天，1表示昨天，2表示前天
     * @return
     */
    @GetMapping("/detail-month/{uid}/{month}")
    public R detailMonth(@RequestParam Map<String, Object> params,
                        @PathVariable("uid") Long uid,
                        @PathVariable(value = "month") Integer month){
        PageUtils detailsVo= dataService.detailMonth(params,uid,month);
        return R.ok().put("page",detailsVo);
    }

    /**
     * 获取今日运动进度
     * @return
     */
    @GetMapping("/scheduleToday/{uid}")
    public R scheduleToday(@PathVariable("uid") Long uid){
        ScheduleVo scheduleVo= dataService.scheduleToday(uid);
        return R.ok().put("data",scheduleVo);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("sport:data:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("sport:data:info")
    public R info(@PathVariable("id") Long id){
		DataEntity data = dataService.getById(id);

        return R.ok().put("data", data);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("sport:data:save")
    public R save(@RequestBody DataEntity data){
		dataService.insert(data);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("sport:data:update")
    public R update(@RequestBody DataEntity data){
		dataService.updateById(data);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("sport:data:delete")
    public R delete(@RequestBody Long[] ids){
		dataService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
