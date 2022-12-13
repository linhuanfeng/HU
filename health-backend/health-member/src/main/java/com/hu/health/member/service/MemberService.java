package com.hu.health.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.member.entity.MemberEntity;
import com.hu.health.member.to.MemberEntityTo;

import java.util.Map;

/**
 * 会员
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-18 12:47:03
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MemberEntity getUserByOpenId(String openId);

    Long insertUser(MemberEntity user);

    void addLikeCount(MemberEntityTo to);

    void subLikeCount(MemberEntityTo to);

    void addCommentCount(MemberEntityTo to);

    void subCommentCount(MemberEntityTo to);

    void addQuestionCount(MemberEntityTo to);

    void subQuestionCount(MemberEntityTo to);

    PageUtils myQueryPage(Map<String, Object> params);

    MemberEntity myGetById(Long id);
}

