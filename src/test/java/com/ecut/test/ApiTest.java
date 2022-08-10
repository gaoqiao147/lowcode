package com.ecut.test;

import com.freesoft.mapper.ApiHeaderMapper;
import com.freesoft.service.ApiHeaderService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

public class ApiTest {
    @Resource
    ApiHeaderService apiHeaderService;

    @Test
    public void test(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("i").append("love").append("you");
        System.out.println(stringBuilder);
    }
}
