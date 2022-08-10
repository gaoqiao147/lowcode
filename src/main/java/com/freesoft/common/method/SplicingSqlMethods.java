package com.freesoft.common.method;

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
        List<ParamsVO> outputParams = generateVO.getOutputParamsList();
        String myWhere = "where";
        String myspace = " ";
        String inParams = getInputParams(inputParams);
        String outParams = getOutputParams(outputParams);
        String tableName = generateVO.getTableName();
        sbSql.append("").append(DML).append(myspace).append(inParams).append(myspace).append("FROM").append(myspace).append(tableName);
        if ("".equals(outputParams)) {
            return sbSql;
        } else {
            System.out.println(outParams);
            sbSql.append(myspace).append(myWhere).append(myspace).append(outParams);
        }
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
     * 将入参转换为一条语句
     *
     * @param outputParams
     * @return
     */
    public String getOutputParams(List<ParamsVO> outputParams) {
        String params = "";
        String question = "= ? ";
        for (int i = 0; i < outputParams.size(); i++) {
            params += outputParams.get(i).getParams() + question + " and ";
        }
        //删除字符串param_type最后的and和？
        params = params.substring(0, params.length() - 7);
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
