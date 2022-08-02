package com.freesoft.controller;


import com.freesoft.service.ApiHeaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 请求头信息表 前端控制器
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@RestController
@RequestMapping("/api-header")
public class ApiHeaderController {
    @Resource
    ApiHeaderService apiHeaderService;
    @GetMapping
    public Object getAll(){
        return apiHeaderService.getApi();
    }
}

