package com.freesoft.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.freesoft.common.method.SaveLogMethods;
import com.freesoft.mapper.ApiLogMapper;
import com.freesoft.model.ApiLogDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 获取http请求内容
 *
 * @author
 */
public class LoggableDispatcherServlet extends DispatcherServlet {
    @Resource
    ApiLogMapper apiLogMapper;

    private static final Logger logger = LoggerFactory.getLogger("HttpLogger");
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        //创建一个 json 对象，用来存放 http 日志信息
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("uri", requestWrapper.getRequestURI());
        rootNode.put("clientIp", requestWrapper.getRemoteAddr());
        rootNode.set("requestHeaders", mapper.valueToTree(getRequestHeaders(requestWrapper)));
        String method = requestWrapper.getMethod();
        rootNode.put("method", method);
        try {
            super.doDispatch(requestWrapper, responseWrapper);
        } finally {
            if ("GET".equals(method)) {
                rootNode.set("request", mapper.valueToTree(requestWrapper.getParameterMap()));
            } else {
                JsonNode newNode = mapper.readTree(requestWrapper.getContentAsByteArray());
                rootNode.set("request", newNode);
            }
            rootNode.put("status", responseWrapper.getStatus());
            JsonNode newNode = mapper.readTree(responseWrapper.getContentAsByteArray());
            rootNode.set("response", newNode);
            responseWrapper.copyBodyToResponse();
            rootNode.set("responseHeaders", mapper.valueToTree(getResponsetHeaders(responseWrapper)));
            //将获取的日志信息存入数据库
            ApiLogDO apiLog = new ApiLogDO();
            Random random = new Random();
            int id = random.nextInt(1000);
            String id_str = String.valueOf(id);
            apiLog.setId(id_str);
            apiLog.setMethod(rootNode.get("method").toString());
            apiLog.setApiId(rootNode.get("uri").toString());
            apiLog.setVisitIp(rootNode.get("clientIp").toString());
            apiLog.setEnterHeader(rootNode.get("responseHeaders").toString());
            apiLog.setResultBody(rootNode.get("response").toString());
            apiLog.setIsPass(rootNode.get("status").toString());
            apiLogMapper.save(apiLog);
        }
    }

    private Map<String, Object> getRequestHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    private Map<String, Object> getResponsetHeaders(ContentCachingResponseWrapper response) {
        Map<String, Object> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers;
    }
}