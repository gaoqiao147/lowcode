package com.freesoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freesoft.mapper.ApiDataSourceMapper;
import com.freesoft.model.ApiDataSourceDO;
import com.freesoft.service.ApiDataSourceService;
import com.freesoft.vo.ColumnNameVO;
import com.freesoft.vo.TableNameVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据源配置表 服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Service
public class ApiDataSourceServiceImpl extends ServiceImpl<ApiDataSourceMapper, ApiDataSourceDO> implements ApiDataSourceService {
    @Resource
    ApiDataSourceMapper apiDataSourceMapper;
    @Override
    public List<TableNameVO> getTableName() {
        return apiDataSourceMapper.getTableName();
    }

    @Override
    public List<ColumnNameVO> getColumnName(String tableName) {
        return apiDataSourceMapper.getColumnName(tableName);
    }
}
