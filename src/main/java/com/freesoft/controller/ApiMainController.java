package com.freesoft.controller;

import com.freesoft.common.enums.MethodEnums;
import com.freesoft.common.enums.ResultStatusEnums;
import com.freesoft.common.method.RequestMethods;
import com.freesoft.common.result.ResponseResult;
import com.freesoft.mapper.ApiLogMapper;
import com.freesoft.mapper.ApiMainMapper;
import com.freesoft.model.ApiMainDO;
import com.freesoft.service.ApiMainService;
import com.freesoft.vo.ParamsVO;
import com.freesoft.vo.RequestUriVO;
import com.freesoft.vo.RequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
    RequestMethods requestMethod;
    @Resource
    ApiMainService apiMainService;

    @GetMapping("/url")
    @ApiOperation(value = "发起请求", httpMethod = "GET")
    public ResponseResult RequestResult(@RequestBody @Validated RequestVO requestVO) {
        String method = requestVO.getMethod();
        String path = requestVO.getPath();
        String token = requestVO.getToken();
        List<ParamsVO> list = requestVO.getParams();
        //判断参数是否正确,isValidEnumIgnoreCase是判断定义的枚举中是否存在method的字符
        if (!EnumUtils.isValidEnumIgnoreCase(MethodEnums.class, method)) {
            return ResponseResult.builder().code(ResultStatusEnums.FAIL.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).build();
        }
        Map<String, Object> map = new HashMap<>();
        if ("GET".equals(method)) {
            map.put("data", requestMethod.methodUseGet(token, method, path).toString());
        }
        if("POST".equals(method)){
            map.put("data", requestMethod.methodUsePost(token, method, path,list).toString());
        }


        return ResponseResult.builder().code(ResultStatusEnums.SUCCESS.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).data(map).build();
    }

    @PostMapping("save-api")
    @ApiOperation(value = "保存接口", httpMethod = "POST")
    public ResponseResult saveApi(@RequestBody @Validated ApiMainDO apiMainDO) {
        //判断参数是否正确,isValidEnumIgnoreCase是判断定义的枚举中是否存在method的字符
        if (!EnumUtils.isValidEnumIgnoreCase(MethodEnums.class, apiMainDO.getMethod())) {
            return ResponseResult.builder().code(ResultStatusEnums.FAIL.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).build();
        }
        int res = apiMainService.saveApi(apiMainDO);
        String data = "成功保存了" + res + "条数据";
        return ResponseResult.builder().code(ResultStatusEnums.SUCCESS.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).data(data).build();
    }

    @GetMapping("get-all-api")
    @ApiOperation(value = "获取所有接口", httpMethod = "GET")
    public ResponseResult getAllApi() {
        List<RequestUriVO> list = apiMainService.getAllApi();
        return ResponseResult.builder().code(ResultStatusEnums.SUCCESS.getCode()).message(ResultStatusEnums.SUCCESS.getMessage()).data(list).build();
    }
}

