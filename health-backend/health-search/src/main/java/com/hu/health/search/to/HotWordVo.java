package com.hu.health.search.to;

import lombok.Data;

import java.util.Date;

@Data
public class HotWordVo {
    private String hotWord;
    private Date time=new Date();
}
