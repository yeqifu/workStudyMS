package com.workstudy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class WorkstudyApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testOne(){
        UUID.randomUUID().toString();
    }
}
