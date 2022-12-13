package com.hu.health.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.hu.health.member.entity.MemberEntity;
import com.hu.health.member.to.MemberEntityTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hu.health.member.service.MemberService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.R;



/**
 * 会员
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-18 12:47:03
 */
@RestController
@RequestMapping("ums/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("sport:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.myQueryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("sport:member:info")
    public R info(@PathVariable("id") Long id) {
		MemberEntity member = memberService.myGetById(id);
//        Thread.sleep(500);
        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("sport:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 增加点赞数量
     */
    @RequestMapping("/addLikeCount")
    // @RequiresPermissions("sport:member:update")
    public R addLikeCount(@RequestBody MemberEntityTo to){
        memberService.addLikeCount(to);
        return R.ok();
    }

    /**
     * 减少点赞数量
     */
    @RequestMapping("/subLikeCount")
    // @RequiresPermissions("sport:member:update")
    public R subLikeCount(@RequestBody MemberEntityTo to){
        memberService.subLikeCount(to);
        return R.ok();
    }

    /**
     * 增加评论数量
     */
    @RequestMapping("/addCommentCount")
    // @RequiresPermissions("sport:member:update")
    public R addCommentCount(@RequestBody MemberEntityTo to){
        memberService.addCommentCount(to);
        return R.ok();
    }

    /**
     * 减少评论数量
     */
    @RequestMapping("/subCommentCount")
    // @RequiresPermissions("sport:member:update")
    public R subCommentCount(@RequestBody MemberEntityTo to){
        memberService.subCommentCount(to);
        return R.ok();
    }

    /**
     * 增加帖子数量
     */
    @RequestMapping("/addQuestionCount")
    // @RequiresPermissions("sport:member:update")
    public R addQuestionCount(@RequestBody MemberEntityTo to){
        memberService.addQuestionCount(to);
        return R.ok();
    }

    /**
     * 减少帖子数量
     */
    @RequestMapping("/subQuestionCount")
    // @RequiresPermissions("sport:member:update")
    public R subQuestionCount(@RequestBody MemberEntityTo to){
        memberService.subQuestionCount(to);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("sport:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("sport:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
