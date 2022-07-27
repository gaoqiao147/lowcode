package com.freesoft.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据源配置表
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_data_source")
public class ApiDataSourceDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 驱动类型 1 mysql 2 oracle
     */
    private String type;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源url
     */
    private String url;

    /**
     * 数据源ip
     */
    private String host;

    /**
     * 数据源端口
     */
    private String port;

    /**
     * 数据库名称
     */
    private String dataBase;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据源描述
     */
    private String description;

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


}
