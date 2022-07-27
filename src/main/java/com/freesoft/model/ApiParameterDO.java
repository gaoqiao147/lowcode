package com.freesoft.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 请求参数信息表
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_parameter")
public class ApiParameterDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 通过接口id查询请求头
     * 接口id
     */
    @NotNull(message = "接口id不能为空")
    private String apiId;

    /**
     * 参数名
     */
    private String key;

    /**
     * 参数默认值
     */
    private String defaultValue;

    /**
     * 参数描述
     */
    private String description;

    /**
     * 是否必填 0 必填 1 非必填
     */
    private String required;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 验证类型 0 不验证 1 表达式验证 2 正则验证
     */
    private String validateType;

    /**
     * 验证不通过说明
     */
    private String error;

    /**
     * 验证表达式
     */
    private String expression;

    /**
     * 排序
     */
    private Integer sort;


}
