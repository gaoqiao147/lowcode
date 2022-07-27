package com.freesoft.service;

import com.freesoft.model.ApiLogDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freesoft.service.impl.ApiLogServiceImpl;

import java.util.List;

/**
 * <p>
 * 接口调用日志表 服务类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiLogService extends IService<ApiLogDO> {
    /**
     * 新建api接口日志
     *
     * @param apiLogDO
     * @return
     */
    int insertApiLog(ApiLogDO apiLogDO);

    /**
     * 查询所有日志信息
     *
     * @return 所有日志信息
     */
    List<ApiLogDO> getAllLog();
}
