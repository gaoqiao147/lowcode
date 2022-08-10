package com.freesoft.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 前端返回给后端，后端根据这些信息生成接口信息，保存接口信息
 * @author zhouwei
 */
@Data
public class GenerateVO {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 操作类型(GET、POST、PUT、DEL)
     */
    private String method;
    /**
     * 输入参数列表
     */
    private List<ParamsVO> inputParamsList;

    private List<DataTypeVO> inputDataType;
    /**
     * 输出参数列表
     */
    private List<ParamsVO> outputParamsList;

    private List<DataTypeVO> outputDataType;

}
