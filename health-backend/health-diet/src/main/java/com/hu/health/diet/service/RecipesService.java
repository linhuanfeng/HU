package com.hu.health.diet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.diet.entity.RecipesEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-17 06:09:21
 */
public interface RecipesService extends IService<RecipesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<RecipesEntity> listWithTree();

    List<RecipesEntity> OnShellList();

    void OntheShells(Long cat_id[]);
    void OfftheShell(Long cat_id[]);

    RecipesEntity getByIdTree(long catId);

}

