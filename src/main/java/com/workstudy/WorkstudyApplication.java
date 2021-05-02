package com.workstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 刘其悦
 */
@SpringBootApplication
@MapperScan("com.workstudy.mapper")
public class WorkstudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkstudyApplication.class, args);
    }

}
