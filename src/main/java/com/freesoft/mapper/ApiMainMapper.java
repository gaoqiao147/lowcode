package com.freesoft.mapper;

import com.freesoft.model.ApiMainDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.freesoft.vo.RequestParamsVO;
import com.freesoft.vo.RequestUriVO;

import java.util.List;

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

    /**
     * 得到所有接口信息（参数）
     *
     * @return
     */
    List<RequestParamsVO> getAllApi();

    /**
     * 通过id查询一条接口信息
     *
     * @param id
     * @return
     */
    RequestParamsVO getAllApiById(Integer id);

    /**
     * 查询所有接口
     *
     * @return java.util.List<com.freesofts.framework.api.model.ApiMain>
     * @author mingHang
     * @date 2022/3/2 17:00
     */
    List<ApiMainDO> getAll();

    ApiMainDO getOneByPath(String path);

}
