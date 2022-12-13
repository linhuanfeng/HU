package com.hu.health.gateway.utils;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class PayLoad<T>{
        private String id;
        private T userInfo;
        private Date expiration;
}