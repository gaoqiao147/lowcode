package com.freesoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhouwei
 */
@SpringBootApplication
@MapperScan("com.freesoft.mapper")
public class LowcodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LowcodeApplication.class, args);
    }
}
