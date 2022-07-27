package com.freesoft.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *【参考资料】
 * https://blog.csdn.net/qq_15452971/article/details/79416469
 * https://blog.csdn.net/weixin_40461281/article/details/83540604
 *
 * @author zhouwei
 */
@Service
public class RestTemplateToInterface {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 以get方式请求第三方http接口 getForEntity
     * @param url
     * @return
     */
    public Object doGetWith1(String url){
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Object obj = responseEntity.getBody();
        return obj;
    }

    /**
     * 以get方式请求第三方http接口 getForObject
     * 返回值返回的是响应体，省去了我们再去getBody()
     * @param url
     * @return
     */
    public Object doGetWith2(String url,String _token){
        //header参数
        HttpHeaders headers = new HttpHeaders();
        String token = _token;
        headers.add("token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //放入body中的json参数
        JSONObject obj = new JSONObject();
        //组装
        HttpEntity<JSONObject> request = new HttpEntity<>(obj, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String body = responseEntity.getBody();
        return body;
    }

    /**
     * 以post方式请求第三方http接口 postForEntity
     * @param url
     * @return
     */
    public String doPostWith1(String url){
        Object Object = new Object();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, Object, String.class);
        String body = responseEntity.getBody();
        return body;
    }

    /**
     * 以post方式请求第三方http接口 postForEntity
     * @param url
     * @return
     */
    public String doPostWith2(String url){
        Object Object = new Object();
        String body = restTemplate.postForObject(url, Object, String.class);
        return body;
    }

    /**
     * exchange
     * @return
     */
    public String doExchange(String url, Integer age, String name){
        //header参数
        HttpHeaders headers = new HttpHeaders();
        String token = "asdfaf2322";
        headers.add("authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //放入body中的json参数
        JSONObject obj = new JSONObject();
        obj.put("age", age);
        obj.put("name", name);

        //组装
        HttpEntity<JSONObject> request = new HttpEntity<>(obj, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        String body = responseEntity.getBody();
        return body;
    }
}
