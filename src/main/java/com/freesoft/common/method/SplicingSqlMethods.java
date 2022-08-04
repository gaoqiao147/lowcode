package com.freesoft.common.method;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.freesoft.vo.GenerateVO;
import com.freesoft.vo.ParamsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouwei
 */
@Service
public class SplicingSqlMethods {
    final static String GET_METHOD = "GET";
    final static String POST_METHOD = "POST";
    final static String PUT_METHOD = "PUT";
    final static String DEL_METHOD = "DELETE";

    public StringBuilder splicingSql(GenerateVO generateVO) {
        StringBuilder sbSql = new StringBuilder();
        String DML = getDML(generateVO.getMethod());
        List<ParamsVO> inputParams = generateVO.getInputParamsList();
        String params = null;
        for (int i = 0; i < inputParams.size(); i++) {
            params = getInputParams(inputParams);
        }
        String tableName = generateVO.getTableName();
        sbSql.append("").append(DML).append(" ").append(params).append(" ").append("FROM").append(" ").append(tableName);
        return sbSql;
    }

    /**
     * 将入参转换为一条语句
     *
     * @param inputParams
     * @return
     */
    public String getInputParams(List<ParamsVO> inputParams) {
        String params = "";
        for (int i = 0; i < inputParams.size(); i++) {
            params += inputParams.get(i).getParams() + ",";
        }
        //删除字符串param_type最后一个逗号
        params = params.substring(0, params.length() - 1);
        return params;
    }

    /**
     * 获取拼接后的请求头
     *
     * @param headers
     * @return
     */
    public String getHeaderStr(String[] headers) {
        String header = "";
        for (int i = 0; i < headers.length; i++) {
            header += headers[i] + ",";
        }
        //删除字符串header最后一个逗号
        return header.substring(0, header.length() - 2);
    }

    public String getDML(String method) {
        String DML = "";
        if (method.equals(GET_METHOD)) {
            DML = "SELECT";
        }
        if (method.equals(POST_METHOD)) {
            DML = "INSERT";
        }
        if (method.equals(PUT_METHOD)) {
            DML = "UPDATE";
        }
        if (method.equals(DEL_METHOD)) {
            DML = "DELETE";
        }
        return DML;
    }
}
