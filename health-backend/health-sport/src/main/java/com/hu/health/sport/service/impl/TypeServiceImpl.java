package com.hu.health.sport.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;

import com.hu.health.sport.dao.TypeDao;
import com.hu.health.sport.entity.TypeEntity;
import com.hu.health.sport.service.TypeService;

import javax.annotation.Resource;

import static com.hu.health.sport.constants.SportConstants.SPORT_TYPE;
import static com.hu.health.sport.constants.SportConstants.TYPE_LOCK;


@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements TypeService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;



    /**
     * 获取运动小贴士，数据读多写少
     * 对象采用hash结构 health:sport:type  id   typeEntity
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        List<TypeEntity> entities= getRedis(params);
        if(entities==null){
            // 采用分布式锁，解决缓存击穿问题
            RLock lock = redissonClient.getLock(TYPE_LOCK);
            lock.lock(); // 休眠阻塞
            entities=getRedis(params); // 双检，别的线程可能已经获取到数据了
            if(entities==null){
                IPage<TypeEntity> page = this.page(
                        new Query<TypeEntity>().getPage(params),
                        new QueryWrapper<TypeEntity>()
                );
                entities=page.getRecords();
                saveToRedis(page); // 保存到缓存中
            }
            lock.unlock(); // 记得解锁
        }
        return new PageUtils(new Page<TypeEntity>(1,entities.size(),entities.size()).setRecords(entities));
    }

    private List<TypeEntity> getRedis(Map<String, Object> params) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SPORT_TYPE);
        List<String> values = hashOps.values();
        List<TypeEntity> entities=null;
        if(values!=null&&!values.isEmpty()){
            entities=values.stream().map(v-> JSON.parseObject(v,TypeEntity.class)).collect(Collectors.toList());
        }
        return entities;
    }


    private void saveToRedis(IPage<TypeEntity> page) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SPORT_TYPE);
        List<TypeEntity> entities = page.getRecords();
        for (TypeEntity entity : entities) {
            hashOps.put(entity.getId().toString(), JSON.toJSONString(entity));
        } // 这里不设置过期时间，因为每个人都要访问，且数据量不大
    }
}