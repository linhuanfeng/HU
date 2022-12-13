package com.hu.health.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.member.entity.ReportEntity;

import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-22 21:18:14
 */
public interface ReportService extends IService<ReportEntity> {

    PageUtils queryPage(Map<String, Object> params);

    ReportEntity result(Integer score);
}

