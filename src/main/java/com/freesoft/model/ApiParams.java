package com.freesoft.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zhouwei
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_params")
public class ApiParams {
    private Integer id;
    private Integer apiId;
    private String key;
    private String dataType;
}
