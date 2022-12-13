package com.hu.health.diet.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hu.health.diet.entity.DietMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;

import com.hu.health.diet.dao.RecipesDao;
import com.hu.health.diet.entity.RecipesEntity;
import com.hu.health.diet.service.RecipesService;


@Service("recipesService")
public class RecipesServiceImpl extends ServiceImpl<RecipesDao, RecipesEntity> implements RecipesService {

    @Autowired
    RecipesDao recipesDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RecipesEntity> page = this.page(
                new Query<RecipesEntity>().getPage(params),
                new QueryWrapper<RecipesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<RecipesEntity> listWithTree() {
        List<RecipesEntity> entities=baseMapper.selectList(null);

        List<RecipesEntity> collect = entities.stream().filter(recipesEntity -> {
            return recipesEntity.getParentCid() == 0;
        }).map((recipes) -> {
            recipes.setChildren(getChildren(recipes, entities));
            return recipes;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<RecipesEntity> OnShellList() {
        List<RecipesEntity> entities=baseMapper.selectList(null);

        List<RecipesEntity> collect = entities.stream().filter(recipesEntity -> {
            return recipesEntity.getParentCid() == 0&&recipesEntity.getShouStatus()==1;
        }).map((recipes) -> {
            recipes.setChildren(getChildrenOnShell(recipes, entities));
            return recipes;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public void OntheShells(Long[] cat_id) {
        UpdateWrapper<RecipesEntity> updateWrapper=new UpdateWrapper<>();
        updateWrapper.in("id",cat_id).set("shou_status",1);
        recipesDao.update(null,updateWrapper);
    }

    @Override
    public void OfftheShell(Long[] cat_id) {
        UpdateWrapper<RecipesEntity> updateWrapper=new UpdateWrapper<>();
        updateWrapper.in("id",cat_id).set("shou_status",0);
        recipesDao.update(null,updateWrapper);
    }

    @Override
    public RecipesEntity getByIdTree(long catId) {
      return null;
    }

    //递归
    private static List<RecipesEntity> getChildren(RecipesEntity root,List<RecipesEntity> all){

        List<RecipesEntity> children = all.stream().filter(recipes -> {
            return recipes.getParentCid() == root.getId();
        }).map(recipes -> {
            recipes.setChildren(getChildren(recipes, all));
            return recipes;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());


        return children;

    }
    private static List<RecipesEntity> getChildrenOnShell(RecipesEntity root,List<RecipesEntity> all){

        List<RecipesEntity> children = all.stream().filter(recipes -> {
            return recipes.getParentCid() == root.getId()&&recipes.getShouStatus()==1;
        }).map(recipes -> {
            recipes.setChildren(getChildren(recipes, all));
            return recipes;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());


        return children;

    }

}