package com.hu.health.selfdiagonsis.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.selfdiagonsis.entity.SelfdiagonsisSymptomEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-19 00:28:15
 */
public interface SelfdiagonsisSymptomService extends IService<SelfdiagonsisSymptomEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<SelfdiagonsisSymptomEntity> listWithTree();
    List<SelfdiagonsisSymptomEntity> listLevel(int level);
    List<SelfdiagonsisSymptomEntity> listWithTreeOnShell();
    void Ontheshelf(long catId);
    void Offtheshelf(long catId);
    void Ontheshelfs(Long[] catId);
    void Offtheshelfs(Long[] catId);

    SelfdiagonsisSymptomEntity getByIdTree(long catId);
}

