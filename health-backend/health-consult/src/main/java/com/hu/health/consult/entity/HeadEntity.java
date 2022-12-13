package com.hu.health.consult.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("consult_head")
public class HeadEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    Long id;
    String head;
    String shortContent;
    String content;
    String img;
}
