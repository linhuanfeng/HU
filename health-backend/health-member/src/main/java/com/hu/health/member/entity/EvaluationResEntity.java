package com.hu.health.member.entity;

import java.io.Serializable;
import java.util.Date;

public class EvaluationResEntity implements Serializable {
    private Long id;
    private Long userId;
    /**
     * 题目id拼接而成，用英文分号分割
     */
    private String evalIds;
    /**
     * 题目数量
     */
    private Integer amount;
    /**
     * 题目总分
     */
    private Integer score;
    private Date createAt;
}
