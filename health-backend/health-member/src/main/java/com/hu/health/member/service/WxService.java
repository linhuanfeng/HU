package com.hu.health.member.service;

import com.hu.health.member.entity.MemberEntity;
import com.hu.health.member.to.LoginInfo;

public interface WxService {
    MemberEntity getUserInfo(LoginInfo loginInfo);
}
