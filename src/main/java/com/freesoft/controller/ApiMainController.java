package com.freesoft.controller;

import com.freesoft.service.impl.RestTemplateToInterface;
import com.freesoft.vo.RequestVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;

/**
 * <p>
 * 接口信息主类 前端控制器
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@RestController
@Validated
@RequestMapping("/api-main")
public class ApiMainController {
    @Resource
    RestTemplateToInterface rt;


    @RequestMapping("/url")
    public Map<String, Object> RequestResult(@RequestBody @Validated RequestVO requestVO, BindingResult result) {
        Map<String, Object> map = new HashMap<>();
        if (result.hasErrors()) {
            map.put("data",result.getFieldError().getDefaultMessage());
            return map;
        } else {
            Object data = new Object();
            String method = requestVO.getMethod();
            String path = requestVO.getPath();
            if ("GET".equals(method)) {
                data = rt.doGetWith2(path,requestVO.getToken());
                System.out.println(data);
            }
            if ("POST".equals(method)) {
                data = rt.doGetWith2(path,requestVO.getToken());
            }
            map.put("data", data);
            return map;
        }
    }
}

