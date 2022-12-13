package com.hu.health.search.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户记录
 */
@Data
public class UserRecordEntity implements Serializable {
    private Long userId;
    private String keyWord;
    private String areaName;
    private String tag;
    private Date createTime=new Date();
}
