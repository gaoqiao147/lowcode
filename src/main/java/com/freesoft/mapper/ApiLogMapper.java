package com.freesoft.mapper;

import com.freesoft.model.ApiLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 接口调用日志表 Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiLogMapper extends BaseMapper<ApiLogDO> {
    /**
     * 新增接口调用日志
     *
     * @param apiLog
     * @return int
     * @author mingHang
     * @date 2022/4/21 18:21
     */
    int save(ApiLogDO apiLog);

}
