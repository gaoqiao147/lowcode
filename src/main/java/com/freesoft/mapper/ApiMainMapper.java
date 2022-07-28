package com.freesoft.mapper;

import com.freesoft.model.ApiMainDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 接口信息主类 Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiMainMapper extends BaseMapper<ApiMainDO> {
    /**
     * 新增接口
     *
     * @param apiMain
     * @return int
     * @author mingHang
     * @date 2022/2/24 20:30
     */
    int insertInterface(ApiMainDO apiMain);

}
