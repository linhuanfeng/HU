package com.hu.health.sleep.controller;

import java.util.Arrays;
import java.util.Map;

import com.hu.health.sleep.entity.MusicEntity;
import com.hu.health.sleep.service.MusicService;
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
 * @date 2022-02-27 15:22:20
 */
@RestController
@RequestMapping("sleep/music")
public class MusicController {
    @Autowired
    private MusicService musicService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("expect:music:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = musicService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("expect:music:info")
    public R info(@PathVariable("id") Long id){
		MusicEntity music = musicService.getById(id);

        return R.ok().put("music", music);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("expect:music:save")
    public R save(@RequestBody MusicEntity music){
		musicService.save(music);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("expect:music:update")
    public R update(@RequestBody MusicEntity music){
		musicService.updateById(music);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("expect:music:delete")
    public R delete(@RequestBody Long[] ids){
		musicService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
