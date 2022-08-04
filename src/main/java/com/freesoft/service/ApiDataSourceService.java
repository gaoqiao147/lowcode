package com.freesoft.service;

import com.freesoft.model.ApiDataSourceDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freesoft.vo.ColumnNameVO;
import com.freesoft.vo.TableNameVO;

import java.util.List;

/**
 * <p>
 * 数据源配置表 服务类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiDataSourceService extends IService<ApiDataSourceDO> {
    /**
     *展示所有表
     * @return
     */
    List<TableNameVO> getTableName();

    /**
     * 获取列信息
     * @param tableName
     * @return
     */
    List<ColumnNameVO> getColumnName(String tableName);
}
