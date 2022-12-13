package com.hu.health.search.to;

import com.hu.health.common.pojo.community.QuestionEntity;
import lombok.Data;

import java.util.List;

@Data
public class SearchResultTo {
    /**
     * 查询到的所有商品
     */
    private List<QuestionEntity> questionEntityList;

    /**
     * 总记录数
     */
    private Long totalCount;
}
