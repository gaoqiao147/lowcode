package com.freesoft.common.method;

import com.freesoft.service.impl.RestTemplateToInterface;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouwei
 */
@Service
public class RequestMethods {
    @Resource
    RestTemplateToInterface rt;

    final static String GRT_METHOD = "GET";
    final static String POST_METHOD = "POST";

    public Object methodUseGet(String token, String method, String path) {
        Object data = new Object();
        if (GRT_METHOD.equals(method)) {
            data = rt.doGet(path, token);
            System.out.println(data);
        }
        return data;
    }

    public Object methodUsePost(String token, String method, String path, List list) {
        Object data = new Object();
        if (POST_METHOD.equals(method)) {
            data = rt.doPost(path, token, list);
        }
        return data;
    }
}
