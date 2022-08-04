package com.freesoft.mapper;

import com.freesoft.model.ApiDataSourceDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.freesoft.vo.ColumnNameVO;
import com.freesoft.vo.TableNameVO;

import java.util.List;

/**
 * <p>
 * 数据源配置表 Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiDataSourceMapper extends BaseMapper<ApiDataSourceDO> {
    /**
     *展示所有表
     * @return
     */
    List<TableNameVO> getTableName();

    /**
     * 展示所有列
     * @param tableName
     * @return
     */
    List<ColumnNameVO> getColumnName(String tableName);

}
