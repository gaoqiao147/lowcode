package com.freesoft.mapper;

import com.freesoft.model.ApiParameterDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 请求参数信息表 Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiParameterMapper extends BaseMapper<ApiParameterDO> {
    /**
     * 保存接口参数信息
     *
     * @param list
     * @return
     */
    int saveApiParams(List<ApiParameterDO> list);
}
