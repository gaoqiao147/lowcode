package com.freesoft.common.method;

import java.util.Date;

import com.freesoft.mapper.ApiLogMapper;
import com.freesoft.model.ApiLogDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author zhouwei
 */
public class SaveLogMethods {
    @Resource
    ApiLogMapper apiLogMapper;

    public void getLog(String visitIp, String MfrId, String MfrName, String Method, String ApiId, String ApiName, String IsPass
            , String EnterHeader, String EnterParam, String ResultBody, String CreatedBy, String CreatedName, String LastModifiedBy, String LastModifiedName) {

        ApiLogDO apiLog = new ApiLogDO();
        Random random = new Random();
        int created_id = random.nextInt(100000);
        String id_str = String.valueOf(created_id);
        apiLog.setId(id_str);
        apiLog.setVisitIp(visitIp);
        apiLog.setMfrId(MfrId);
        apiLog.setMfrName(MfrName);
        apiLog.setMethod(Method);
        apiLog.setApiId(ApiId);
        apiLog.setApiName(ApiName);
        apiLog.setIsPass(IsPass);
        apiLog.setEnterHeader(EnterHeader);
        apiLog.setEnterParam(EnterParam);
        apiLog.setResultBody(ResultBody);
        apiLog.setCreatedBy(CreatedBy);
        apiLog.setCreatedName(CreatedName);
        apiLog.setCreatedDate(new Date());
        apiLog.setLastModifiedBy(LastModifiedBy);
        apiLog.setLastModifiedName(LastModifiedName);
        apiLog.setLastModifiedDate(new Date());
        apiLogMapper.save(apiLog);
    }
}
