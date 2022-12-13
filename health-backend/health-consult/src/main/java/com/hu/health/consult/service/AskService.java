package com.hu.health.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.consult.entity.AskEntity;

import java.util.Map;

public interface AskService extends IService<AskEntity> {
    PageUtils queryPage(Map<String, Object> params);

}
