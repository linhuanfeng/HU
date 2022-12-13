package com.hu.health.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hu.health.community.entity.AreaEntity;
import com.hu.health.community.entity.AreaMemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-21 22:55:13
 */
@Mapper
public interface AreaMemberDao extends BaseMapper<AreaMemberEntity> {

    @Select("select a.* from community_area a,community_area_member am where a.id=am.area_id and am.member_id=#{memberId}")
    IPage<AreaEntity> selectLikeAreas(IPage<AreaEntity> page, @Param("memberId") Long memberId);
}
