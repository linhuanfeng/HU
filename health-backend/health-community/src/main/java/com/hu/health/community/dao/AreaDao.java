package com.hu.health.community.dao;

import com.hu.health.community.entity.AreaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-20 20:53:36
 */
@Mapper
public interface AreaDao extends BaseMapper<AreaEntity> {
    @Update("update community_area set like_count=like_count+1")
    void follow(Long id);

    @Update("update community_area set like_count=like_count-1")
    void unFollow(Long id);
}
