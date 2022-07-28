package com.freesoft.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 接口信息主类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_main")
public class ApiMainDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 接口名称
     */
    @NotBlank(message = "接口不能为空")
    private String name;

    /**
     * 请求方式
     */
    @NotBlank(message = "请求方式不能为空")
    private String method;

    /**
     * 请求路径（去掉前缀）
     */
    @NotBlank(message = "请求路径不能为空")
    private String path;


    /**
     * 接口描述
     */
    @NotBlank(message = "接口描述不能为空")
    private String description;

    /**
     * 接口状态 0 正常 1 注销
     */
    private String state;

    /**
     * 是否启用数据源  0 启用  1 不启用
     */
    private String enable;

    /**
     * 执行语句
     */
    private String executeSql;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建人名称
     */
    private String createdName;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 最后修改人
     */
    private String lastModifiedBy;

    /**
     * 最后修改人名称
     */
    private String lastModifiedName;

    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;

    /**
     * 请求参数列表
     */
    @ApiModelProperty(notes = "请求参数")
    private List<ApiParameterDO> parameters =  Lists.newArrayList();

}
