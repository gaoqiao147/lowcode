package com.freesoft.controller;

import com.freesoft.common.enums.MethodEnums;
import com.freesoft.common.enums.ResultStatusEnums;
import com.freesoft.common.method.RequestMethod;
import com.freesoft.common.result.ResponseResult;
import com.freesoft.vo.RequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 接口信息主类 前端控制器
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Api(tags = "数据发起请求列表操作")
@RestController
@Validated
@RequestMapping("/api-main")
public class ApiMainController {
    @Resource
    private RequestMethod requestMethod;

    @RequestMapping("/url")
    @ApiOperation(value = "发起请求", httpMethod = "GET")
    public ResponseResult RequestResult(@RequestBody @Validated RequestVO requestVO) {
        String method = requestVO.getMethod();
        String path = requestVO.getPath();
        String token = requestVO.getToken();
        //判断参数是否正确,isValidEnumIgnoreCase是判断定义的枚举中是否存在method的字符
        if (!EnumUtils.isValidEnumIgnoreCase(MethodEnums.class, method)) {
            return ResponseResult.builder().code(ResultStatusEnums.FAIL.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).build();
        }
        Object data = requestMethod.methodUse(token, method, path);
        return ResponseResult.builder().code(ResultStatusEnums.SUCCESS.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).data(data).build();
    }
}

