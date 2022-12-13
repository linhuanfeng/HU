package com.hu.health.selfdiagonsis.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.hu.health.common.utils.PageUtils;
//import com.hu.health.common.utils.Query;

import com.hu.health.selfdiagonsis.dao.SelfdiagonsisSymptomDao;
import com.hu.health.selfdiagonsis.entity.SelfdiagonsisSymptomEntity;
import com.hu.health.selfdiagonsis.service.SelfdiagonsisSymptomService;


@Service("selfdiagonsisSymptomService")
public class SelfdiagonsisSymptomServiceImpl extends ServiceImpl<SelfdiagonsisSymptomDao, SelfdiagonsisSymptomEntity> implements SelfdiagonsisSymptomService {
@Autowired
    private SelfdiagonsisSymptomDao selfdiagonsisSymptomDao;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SelfdiagonsisSymptomEntity> page = this.page(
                new Query<SelfdiagonsisSymptomEntity>().getPage(params),
                new QueryWrapper<SelfdiagonsisSymptomEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SelfdiagonsisSymptomEntity> listWithTree() {
        List<SelfdiagonsisSymptomEntity> entities=baseMapper.selectList(null);

        List<SelfdiagonsisSymptomEntity> collect = entities.stream().filter(selfdia -> {
            return selfdia.getParentCid() == 0;
        }).map(self -> {
           self.setChildren(getChildren(self, entities));
            return self;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<SelfdiagonsisSymptomEntity> listLevel(int level) {
        return null;

    }
    private static List<SelfdiagonsisSymptomEntity> getChildren(SelfdiagonsisSymptomEntity root,List<SelfdiagonsisSymptomEntity> all){

        List<SelfdiagonsisSymptomEntity> children = all.stream().filter(selfdia -> {
            return selfdia.getParentCid() == root.getCatId();
        }).map(self -> {
            self.setChildren(getChildren(self, all));
            return self;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());


        return children;

    }

    @Override
    public List<SelfdiagonsisSymptomEntity> listWithTreeOnShell() {
        List<SelfdiagonsisSymptomEntity> entities=baseMapper.selectList(null);
        List<SelfdiagonsisSymptomEntity> collect = entities.stream().filter(selfdia -> {
            return selfdia.getParentCid() == 0&&selfdia.getShowStatus()==1;
        }).map(self -> {
            self.setChildren(getChildrenOnShell(self, entities));
            return self;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());

        return collect;
    }



    private static List<SelfdiagonsisSymptomEntity> getChildrenOnShell(SelfdiagonsisSymptomEntity root,List<SelfdiagonsisSymptomEntity> all){

        List<SelfdiagonsisSymptomEntity> children = all.stream().filter(selfdia -> {
            return selfdia.getParentCid() == root.getCatId()&&selfdia.getShowStatus()==1;
        }).map(self -> {
            self.setChildren(getChildrenOnShell(self, all));
            return self;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());


        return children;

    }



    @Override
    public void Ontheshelf(long catId) {
        UpdateWrapper<SelfdiagonsisSymptomEntity> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("cat_id",catId).set("show_status",1);
        selfdiagonsisSymptomDao.update(null,updateWrapper);


    }

    @Override
    public void Offtheshelf(long catId) {
        UpdateWrapper<SelfdiagonsisSymptomEntity> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("cat_id",catId).set("show_status",0);
        selfdiagonsisSymptomDao.update(null,updateWrapper);
    }

    @Override
    public void Ontheshelfs(Long[] catId) {


            UpdateWrapper<SelfdiagonsisSymptomEntity> updateWrapper=new UpdateWrapper<>();
            updateWrapper.in("cat_id",catId).set("show_status",1);
            selfdiagonsisSymptomDao.update(null,updateWrapper);
    }

    @Override
    public void Offtheshelfs(Long[] catId) {
        UpdateWrapper<SelfdiagonsisSymptomEntity> updateWrapper=new UpdateWrapper<>();
            updateWrapper.in("cat_id",catId).set("show_status",0);
            selfdiagonsisSymptomDao.update(null,updateWrapper);

    }

    @Override
    public SelfdiagonsisSymptomEntity getByIdTree(long catId) {
        List<SelfdiagonsisSymptomEntity> entities=baseMapper.selectList(null);
        List<SelfdiagonsisSymptomEntity> collect = entities.stream().filter(selfdia -> {
            return selfdia.getCatId()==catId&&selfdia.getShowStatus()==1;
        }).map(self -> {
            self.setChildren(getChildrenOnShell(self, entities));
            return self;
        }).sorted((menu1, menu2) -> {
            return menu1.getSort() - menu2.getSort();
        }).collect(Collectors.toList());
        return  collect.get(0);
    }
}