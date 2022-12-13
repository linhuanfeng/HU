package com.hu.health.community.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;
import com.hu.health.community.entity.AreaMemberEntity;
import com.hu.health.community.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hu.health.community.service.AreaMemberService;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.sport.controller.R;



/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-21 22:55:13
 */
@RestController
@RequestMapping("community/areamember")
public class AreaMemberController {
    @Autowired
    private AreaMemberService areaMemberService;

    @Autowired
    private AreaService areaService;

    /**
     * 列表
     */
        @RequestMapping("/areas/{memberId}")
    public R likeAreas(@PathVariable("memberId") Long memberId){
            Map<String,Object> params=new HashMap<>();
            params.put("page","1");
            params.put("limit","10");
        PageUtils page = areaMemberService.likeAreas(params,memberId);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = areaMemberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AreaMemberEntity areaMember = areaMemberService.getById(id);

        return R.ok().put("areaMember", areaMember);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AreaMemberEntity areaMember){
		areaMemberService.save(areaMember);

		// 帖子关注数添加
        areaService.follow(areaMember.getAreaId());

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AreaMemberEntity areaMember){
		areaMemberService.updateById(areaMember);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		areaMemberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteEntity")
    public R deleteEntity(@RequestBody AreaMemberEntity areaMemberEntity){
        areaMemberService.removeById(areaMemberEntity.getMemberId());

        // area关注者减一
        areaService.unFollow(areaMemberEntity.getAreaId());

        return R.ok();
    }

}
