package com.hu.health.sport.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SportDataDetailsVo implements Serializable {
    private Integer durations;
    private String typeName;
    private String typeLevel;
    private String tip;
    private String smallImg;
    private String bigImg;
}
