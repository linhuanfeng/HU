package com.hu.health.consult.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hu.health.consult.entity.AskEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class AskEntityDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type=IdType.AUTO)
    Long id;
    Long fromMemberId;
    Long toMemberId;
    String content;
    String[] file;

}
