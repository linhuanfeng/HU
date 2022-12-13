package com.hu.health.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ums_report")
public class ReportEntity {
    private static final long serialVersionUID = 1L;

    Long id;
    Integer score;
    String dietSuggestion;
    String sportSuggestion;
    String sleepSuggestion;
    String description;
}
