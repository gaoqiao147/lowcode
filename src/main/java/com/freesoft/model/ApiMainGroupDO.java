package com.freesoft.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接口与分组关联信息表
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_main_group")
public class ApiMainGroupDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 接口主表id
     */
    @TableId(value = "api_id", type = IdType.AUTO)
    private String apiId;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 0 创建分组 1 引用分组
     */
    private String quote;


}
