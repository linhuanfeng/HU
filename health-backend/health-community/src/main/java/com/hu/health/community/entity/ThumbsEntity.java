package com.hu.health.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 点赞记录实体
 */
@Data
@TableName("community_thumbs")
public class ThumbsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 点赞id
     */
    @TableId
    private Long id;
    /**
     * 帖子id或评论id
     */
    private Long entityId;
    /**
     * 点赞的用户id
     */
    private Long memberId;
    /**
     * 点赞类型：0表示帖子，1表示评论
     */
    private Integer type = EntityType.questionType.key;

    public enum EntityType{
        questionType(0,"帖子"),commentType(1,"评论");
        int key;
        String value;

        EntityType(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

}
