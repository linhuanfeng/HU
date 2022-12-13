package com.hu.health.common.pojo;

import com.hu.health.common.utils.ObjectUtil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
@Data
@ToString
public class SearchParam implements Serializable {
    private Long userId;
    private String keyword="";
    private String areaName="";
    private String tag="";
    private Integer from=1;
    private Integer size=20;

    public static SearchParam parse(Map<String,Object> map){
        SearchParam searchParam = ObjectUtil.MapToObjBySetter(map, SearchParam.class);
        return searchParam;
    }
}
