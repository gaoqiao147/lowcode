package com.freesoft.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.freesoft.service.ApiMainService;
import com.freesoft.vo.ParamsVO;
import com.freesoft.vo.RequestUriVO;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 【参考资料】
 * https://blog.csdn.net/qq_15452971/article/details/79416469
 * https://blog.csdn.net/weixin_40461281/article/details/83540604
 *
 * @author zhouwei
 */
@Configuration
public class RestTemplateToInterface {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    ApiMainService apiMainService;

    /**
     * 以get方式请求第三方http接口 getForObject
     * 返回值返回的是响应体，省去了我们再去getBody()
     *
     * @param url
     * @return
     */
    public Object doGet(String url, String _token) {
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
     * post
     *
     * @return
     */
    public String doPost(String url, String _token, List<ParamsVO> list) {
        //header参数
        HttpHeaders headers = new HttpHeaders();
        String token = _token;
        headers.add("token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //放入body中的json参数
        List<RequestUriVO> listParams = apiMainService.getAllApi();
        List<String> lists = listParams.get(0).getParams();
        System.out.println("参数" + lists);
        System.out.println("参数值" + list);
        JSONObject obj = new JSONObject();
        for (int i = 0; i < lists.size(); i++) {
            obj.put(lists.get(i), list.get(i).getParams());
        }
        //组装
        HttpEntity<JSONObject> request = new HttpEntity<>(obj, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        String body = responseEntity.getBody();
        return body;
    }
}
