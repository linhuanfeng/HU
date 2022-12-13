package com.hu.health.diet;

import com.hu.health.diet.dao.DayDao;
import com.hu.health.diet.entity.DietMenuEntity;
import com.hu.health.diet.service.DayService;
import com.hu.health.diet.service.DietMenuService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HealthDietApplicationTests {


    @Autowired
    DayService dayService;

    @Test
    void contextLoads() {
      dayService.showBreakfast(1L,"2");
    }

}
