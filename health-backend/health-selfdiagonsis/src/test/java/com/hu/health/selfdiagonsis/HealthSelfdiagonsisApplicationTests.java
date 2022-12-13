package com.hu.health.selfdiagonsis;

import com.hu.health.selfdiagonsis.service.SelfdiagonsisSymptomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HealthSelfdiagonsisApplicationTests {
    @Autowired
    SelfdiagonsisSymptomService selfdiagonsisSymptomService;
    @Test
    void contextLoads() {
        System.out.println(selfdiagonsisSymptomService.getByIdTree(2));

    }

}
