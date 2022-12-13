package com.hu.health.consult.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hu.health.consult.dto.AskEntityDto;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("consult_ask")
public class AskEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type=IdType.AUTO)
    Long id;
    Long fromMemberId;
    Long toMemberId;
    String content;
    String file="";
    public AskEntity(AskEntityDto askEntityDto){
            this.fromMemberId=askEntityDto.getFromMemberId();
            this.toMemberId=askEntityDto.getToMemberId();
            this.content=askEntityDto.getContent();
            for (String s : askEntityDto.getFile()) {
                this.file=this.file+s;
            }
    }
}
