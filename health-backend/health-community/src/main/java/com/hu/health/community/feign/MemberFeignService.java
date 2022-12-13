package com.hu.health.community.feign;

import com.hu.health.common.utils.R;
import com.hu.health.community.to.MemberEntityTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name ="health-member" )
public interface MemberFeignService {
    /**
     * 增加点赞数量
     */
    @RequestMapping("ums/member/addLikeCount")
    // @RequiresPermissions("sport:member:update")
    R addLikeCount(@RequestBody MemberEntityTo to);

    /**
     * 减少点赞数量
     */
    @RequestMapping("ums/member/subLikeCount")
    // @RequiresPermissions("sport:member:update")
    R subLikeCount(@RequestBody MemberEntityTo to);

    /**
     * 增加评论数量
     */
    @RequestMapping("ums/member/addCommentCount")
    // @RequiresPermissions("sport:member:update")
    R addCommentCount(@RequestBody MemberEntityTo to);

    /**
     * 减少评论数量
     */
    @RequestMapping("ums/member/subCommentCount")
    // @RequiresPermissions("sport:member:update")
    R subCommentCount(@RequestBody MemberEntityTo to);

    /**
     * 增加帖子数量
     * @param to
     * @return
     */
    @RequestMapping("ums/member/addQuestionCount")
    // @RequiresPermissions("sport:member:update")
    R addQuestionCount(@RequestBody MemberEntityTo to);

    /**
     * 减少帖子数量
     * @param to
     * @return
     */
    @RequestMapping("ums/member/subQuestionCount")
    // @RequiresPermissions("sport:member:update")
    R subQuestionCount(@RequestBody MemberEntityTo to);
}
