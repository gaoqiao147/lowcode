package com.freesoft.vo;

import lombok.Data;

/**
 * @author zhouwei
 */
@Data
public class RequestParamsVO {
    private Integer apiId;
    private String path;
    private String method;
    private String key;
}
