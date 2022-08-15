package com.freesoft.mapper;

import com.freesoft.model.ApiParameterDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.freesoft.model.ApiParams;
import com.freesoft.vo.DataTypeVO;
import com.freesoft.vo.ParamsVO;

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

    /**
     * 保存接口参数信息(输入参数)
     *
     * @param list
     * @return
     */
    int saveApiParamsOut(List<ApiParams> list);
    /**
     * 根据apiId查询参数名(用户要填的参数信息)
     * @param apiId
     * @return
     */
    List<ParamsVO> paramsList(Integer apiId);

    /**
     * 根据apiId查询参数名(用户选择要输出的参数)
     * @param apiId
     * @return
     */
    List<ParamsVO> paramsList2(Integer apiId);
    /**
     * 根据apiId查询参数类型
     * @param apiId
     * @return
     */
    List<DataTypeVO> paramsDataTypeList(Integer apiId);
}
