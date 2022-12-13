package com.hu.health.diet.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

import com.hu.health.diet.dao.DietMenuDao;
import com.hu.health.diet.entity.DietMenuEntity;
import com.hu.health.diet.service.DietMenuService;


@Service("dietMenuService")
public class DietMenuServiceImpl extends ServiceImpl<DietMenuDao, DietMenuEntity> implements DietMenuService {

    @Autowired
    DietMenuDao dietMenuDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DietMenuEntity> page = this.page(
                new Query<DietMenuEntity>().getPage(params),
                new QueryWrapper<DietMenuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<DietMenuEntity> listWithTree() {
        List<DietMenuEntity> entities=baseMapper.selectList(null);

        List<DietMenuEntity> collect = entities.stream().filter(dietMenuEntity -> {
            return dietMenuEntity.getParentCid() == 0;
        }).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<DietMenuEntity> OnShelllist() {
        List<DietMenuEntity> entities=baseMapper.selectList(null);

        List<DietMenuEntity> collect = entities.stream().filter(dietMenuEntity -> {
            return dietMenuEntity.getParentCid() == 0&&dietMenuEntity.getShowStatus()==1;
        }).map((menu) -> {
            menu.setChildren(getChildrenOnShell(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public void OnTheShell(Long cat_id[]) {
        UpdateWrapper <DietMenuEntity> wrapper=new UpdateWrapper<>();
        wrapper.in("cat_id",cat_id).set("show_status",1);
        dietMenuDao.update(null,wrapper);
    }

    @Override
    public void OffTheShell(Long cat_id[]) {
        UpdateWrapper<DietMenuEntity> wrapper=new UpdateWrapper<>();
        wrapper.in("cat_id",cat_id).set("show_status",0);
        dietMenuDao.update(null,wrapper);

    }

    @Override
    public DietMenuEntity getByIdTree(long catId) {
        List<DietMenuEntity> entities=baseMapper.selectList(null);
        List<DietMenuEntity> collect = entities.stream().filter(selfdia -> {
            return selfdia.getCatId()==catId&&selfdia.getShowStatus()==1;
        }).map(self -> {
            self.setChildren(getChildrenOnShell(self, entities));
            return self;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());
        return  collect.get(0);
    }

    //递归
    private static List<DietMenuEntity> getChildren(DietMenuEntity root,List<DietMenuEntity> all){

        List<DietMenuEntity> children = all.stream().filter(dietMenu -> {
            return dietMenu.getParentCid() == root.getCatId();
        }).map(dietMenu -> {
            dietMenu.setChildren(getChildren(dietMenu, all));
            return dietMenu;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());


        return children;

    }
    private static List<DietMenuEntity> getChildrenOnShell(DietMenuEntity root,List<DietMenuEntity> all){

        List<DietMenuEntity> children = all.stream().filter(dietMenu -> {
            return dietMenu.getParentCid() == root.getCatId()&&dietMenu.getShowStatus()==1;
        }).map(dietMenu -> {
            dietMenu.setChildren(getChildren(dietMenu, all));
            return dietMenu;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());


        return children;

    }
}