package com.hu.health.diet.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.diet.entity.DietMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-02-14 20:50:23
 */
public interface DietMenuService extends IService<DietMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DietMenuEntity> listWithTree();

    List<DietMenuEntity> OnShelllist();

    void OnTheShell(Long cat_id[]);
    void OffTheShell(Long cat_id[]);

    DietMenuEntity getByIdTree(long catId);

}

