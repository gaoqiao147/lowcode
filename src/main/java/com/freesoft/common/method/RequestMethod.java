package com.freesoft.common.method;

import com.freesoft.service.impl.RestTemplateToInterface;

import javax.annotation.Resource;

/**
 * @author zhouwei
 */
public class RequestMethod {
    @Resource
    RestTemplateToInterface rt;

    final static String GRT_METHOD = "GET";
    final static String POST_METHOD = "POST";

    public Object methodUse(String token,String method,String path){
        Object data = new Object();
        if (GRT_METHOD.equals(method)) {
            data = rt.doGet(path, token);
            System.out.println(data);
        }
        if (POST_METHOD.equals(method)) {
            data = rt.doPost(path, token);
        }
        return data;
    }
}
