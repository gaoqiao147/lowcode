package com.freesoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.freesoft.model.ApiLogDO;
import com.freesoft.mapper.ApiLogMapper;
import com.freesoft.service.ApiLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 接口调用日志表 服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Service
public class ApiLogServiceImpl extends ServiceImpl<ApiLogMapper, ApiLogDO> implements ApiLogService {
    @Resource
    ApiLogMapper apiLogMapper;

    @Override
    public int insertApiLog(ApiLogDO apiLogDO) {
        return apiLogMapper.save(apiLogDO);
    }

    @Override
    public List<ApiLogDO> getAllLog() {
        return apiLogMapper.selectList(null);
    }
}
