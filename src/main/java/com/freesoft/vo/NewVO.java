package com.freesoft.vo;

import lombok.Data;

/**
 * @author zhouwei
 */
@Data
public class NewVO {
    private Integer apiId;
    private String path;
    private String method;
    private String token;
    private String params;
    private String paramsDataType;
}
