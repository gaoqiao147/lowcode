package com.freesoft.service.impl;

import com.freesoft.model.ApiLogDO;
import com.freesoft.mapper.ApiLogMapper;
import com.freesoft.service.ApiLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
