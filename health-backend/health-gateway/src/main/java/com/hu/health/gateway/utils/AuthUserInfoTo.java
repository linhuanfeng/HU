package com.hu.health.gateway.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class AuthUserInfoTo {
    private Long id;
    private String nickname;
}
