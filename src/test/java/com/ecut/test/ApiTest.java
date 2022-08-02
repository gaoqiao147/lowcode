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
        System.out.println(apiHeaderService.getApi());
    }
}
