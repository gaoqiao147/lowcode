package com.freesoft.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 请求头信息表
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_header")
public class ApiHeaderDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 接口id
     */
    private String apiId;

    /**
     * 参数名
     */
    @NotBlank(message = "参数名不能为空！")
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
     * 排序
     */
    private Integer sort;


}
