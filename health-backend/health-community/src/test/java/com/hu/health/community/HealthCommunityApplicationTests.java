package com.hu.health.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;


@SpringBootTest
public class HealthCommunityApplicationTests {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void listPush() {
        BoundListOperations<String, String> listOps = redisTemplate.boundListOps("comment:lasted");
        for (int i = 0; i < 10; i++) {
            listOps.rightPush("c"+i);
        }
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,random.nextInt(15));
        listOps.expireAt(calendar.getTime());
    }

    @Test
    public void listRange() {
//        redisTemplate.boundListOps("comment:lasted").range()
    }
}
