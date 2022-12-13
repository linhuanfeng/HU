package com.hu.health.member.controller;

import com.hu.health.common.utils.R;
import com.hu.health.member.entity.MemberEntity;
import com.hu.health.member.service.WxService;
import com.hu.health.member.to.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ums/wx")
@Slf4j
public class WXController {

    @Autowired
    WxService wxService;

    /**
     * 信息
     */
    @PostMapping("/decryptInfo")
    // @RequiresPermissions("sport:member:info")
    public R decryptInfo(@RequestBody LoginInfo loginInfo){
        log.debug("要解密的loginInfo为{}",loginInfo);
        MemberEntity member = wxService.getUserInfo(loginInfo);
        log.debug("解密完的MemberEntity为{}",member);
        return R.ok().put("member", member);
    }

}
