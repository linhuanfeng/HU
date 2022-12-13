package com.hu.health.search.entity;

import lombok.Data;

@Data
public class AreaAndTagEntity {
    private Long userId;
    private String areaName="";
    private String tag="";
}
