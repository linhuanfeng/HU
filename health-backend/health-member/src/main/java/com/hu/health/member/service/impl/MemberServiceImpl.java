package com.hu.health.member.service.impl;

import com.hu.health.member.entity.MemberEntity;
import com.hu.health.member.to.MemberEntityTo;
import com.hu.health.member.utils.CalBMIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;

import com.hu.health.member.dao.MemberDao;
import com.hu.health.member.service.MemberService;
import org.springframework.transaction.annotation.Transactional;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public MemberEntity getUserByOpenId(String openId) {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("open_id", openId));
        return memberEntity;
    }

    @Override
    public Long insertUser(MemberEntity user) {
        return  (long)this.baseMapper.insert(user);
    }

    @Override
    public void addLikeCount(MemberEntityTo to) {
        memberDao.addLikeCount(to.getMemberId(),to.getCount());
    }

    @Override
    public void subLikeCount(MemberEntityTo to) {
        memberDao.subLikeCount(to.getMemberId(), to.getCount());
    }

    @Override
    @Transactional
    public void addCommentCount(MemberEntityTo to) {
        memberDao.addCommentCount(to.getMemberId(),to.getCount());
    }

    @Override
    public void subCommentCount(MemberEntityTo to) {
        memberDao.subCommentCount(to.getMemberId(), to.getCount());
    }

    @Override
    public void addQuestionCount(MemberEntityTo to) {
        memberDao.addQuestionCount(to.getMemberId(),to.getCount());
    }

    @Override
    public void subQuestionCount(MemberEntityTo to) {
        memberDao.subQuestionCount(to.getMemberId(),to.getCount());
    }

    @Override
    public PageUtils myQueryPage(Map<String, Object> params) {
        PageUtils page = this.queryPage(params);
        // 计算BMI sa
        calBMI(page);
        return page;
    }

    private void calBMI(PageUtils page) {
        page.setList(page.getList().stream().map(o -> {
            MemberEntity entity=(MemberEntity)o;
            entity.setBMI(CalBMIUtil.calBMI(entity.getWeight(),entity.getHeight()));
            return entity;
        }).collect(Collectors.toList()));
    }



    @Override
    public MemberEntity myGetById(Long id) {
        MemberEntity entity = this.getById(id);
        entity.setBMI(CalBMIUtil.calBMI(entity.getWeight(),entity.getHeight()));
        return entity;
    }

}