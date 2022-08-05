package com.freesoft.controller;

import com.freesoft.service.ApiMainService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 注册api controller
 *
 * @author mingHang
 * @date 2022-02-24 18:07
 */
@Api(tags = "注册api controller")
@RestController
@RequestMapping
public class RequestHandler {

    private ApiMainService apiService;

    public RequestHandler(ApiMainService apiService) {
        this.apiService = apiService;
    }

    /**
     * 实际请求入口
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param pathVariables  路径变量
     * @param defaultHeaders 请求头
     * @param parameters     表单参数&URL参数
     * @return com.freesofts.common.response.BizResponseData<?>
     */
    @ResponseBody
    public Object invoke(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable(required = false) Map<String, Object> pathVariables,
                         @RequestHeader(required = false) Map<String, Object> defaultHeaders,
                         @RequestParam(required = false) Map<String, Object> parameters,
                         @RequestBody(required = false) Map<String, Object> body) throws Throwable {
        //两类参数合并处理
        Map<String, Object> defaultParameters = Maps.newHashMap();
        if (null != parameters && !parameters.isEmpty()) {
            defaultParameters.putAll(parameters);
        }
        if (null != body && !body.isEmpty()) {
            defaultParameters.putAll(body);
        }
        return apiService.invoke(request, defaultHeaders, defaultParameters);
    }

}
