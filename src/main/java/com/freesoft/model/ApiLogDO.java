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
 * 接口调用日志表
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_log")
public class ApiLogDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 访问ip
     */
    private String visitIp;

    /**
     * 厂商名称id
     */
    private String mfrId;

    /**
     * 厂商名称
     */
    private String mfrName;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 接口id
     */
    private String apiId;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 调用是否通过
     */
    private String isPass;

    /**
     * 入参请求头
     */
    private String enterHeader;

    /**
     * 入参请求体
     */
    private String enterParam;

    /**
     * 返回结果
     */
    private String resultBody;

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
