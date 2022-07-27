package com.freesoft.controller;


import com.freesoft.common.enums.ResultStatusEnums;
import com.freesoft.common.method.RequestMethods;
import com.freesoft.common.result.ResponseResult;
import com.freesoft.config.LoggableDispatcherServlet;
import com.freesoft.mapper.ApiLogMapper;
import com.freesoft.service.ApiLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 接口调用日志表 前端控制器
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Api(tags = "接口日志管理")
@RestController
@RequestMapping("/api-log")
public class ApiLogController {
    @Resource
    ApiLogService apiLogService;

    @GetMapping("/get-all-log")
    @ApiOperation(value = "获取所有日志信息", notes = "得到日志数组")
    public ResponseResult getLog(){
        List LogList = apiLogService.getAllLog();
        if(null == LogList){
            return ResponseResult.builder().code(ResultStatusEnums.FAIL.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).build();
        }else {
            return ResponseResult.builder().code(ResultStatusEnums.SUCCESS.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).data(LogList).build();
        }
    }
}

