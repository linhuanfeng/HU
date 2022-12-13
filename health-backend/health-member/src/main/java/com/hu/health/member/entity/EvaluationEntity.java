package com.hu.health.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ums_evaluation")
public class EvaluationEntity {
    private static final long serialVersionUID = 1L;

    Long id;
    String choiceA;
    String choiceB;
    String choiceC;
    String choiceD;
    String origin;
    Date createDate;
}
