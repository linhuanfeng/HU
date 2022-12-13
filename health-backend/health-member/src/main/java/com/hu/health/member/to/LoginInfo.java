package com.hu.health.member.to;

import lombok.Data;
import lombok.ToString;

/**
 * 微信登录后返回的用户加密信息
 */
@Data
@ToString
public class LoginInfo {
    String code;
    String encryptedData;
    String iv;
}