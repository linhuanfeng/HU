package com.hu.health.member.dao;

import com.hu.health.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 会员
 * 
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-18 12:47:03
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
    // 更新语句考虑乐观锁
	@Update("update ums_member set like_count=like_count+#{count} where id=#{uid}")
    void addLikeCount(@Param("uid") Long uid,@Param("count") Integer count);

    @Update("update ums_member set like_count=like_count-#{count} where id=#{uid} and like_count>=#{count}")
    void subLikeCount(@Param("uid") Long uid,@Param("count") Integer count);

    @Update("update ums_member set comment_count=comment_count+#{count} where id=#{uid}")
    void addCommentCount(@Param("uid") Long uid,@Param("count") Integer count);

    @Update("update ums_member set comment_count=comment_count-#{count} where id=#{uid} and comment_count>=#{count}")
    void subCommentCount(@Param("uid") Long uid,@Param("count") Integer count);

    @Update("update ums_member set question_count=question_count+#{count} where id=#{uid}")
    void addQuestionCount(@Param("uid") Long uid,@Param("count") Integer count);

    @Update("update ums_member set question_count=question_count-#{count} where id=#{uid} and question_count>=#{count}")
    void subQuestionCount(@Param("uid") Long uid,@Param("count") Integer count);
}
