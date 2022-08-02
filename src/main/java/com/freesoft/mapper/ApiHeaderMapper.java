package com.freesoft.mapper;

import com.freesoft.model.ApiHeaderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.freesoft.vo.NewVO;

import java.util.List;

/**
 * <p>
 * 请求头信息表 Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiHeaderMapper extends BaseMapper<ApiHeaderDO> {
    List<NewVO> getAllByApiIdNewVos();
}
