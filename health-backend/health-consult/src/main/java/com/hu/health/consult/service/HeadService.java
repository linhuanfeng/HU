package com.hu.health.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.consult.entity.HeadEntity;

import java.util.Map;

public interface HeadService extends IService<HeadEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
