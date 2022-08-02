package com.freesoft.service;

import com.freesoft.model.ApiHeaderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freesoft.vo.NewVO;

import java.util.List;

/**
 * <p>
 * 请求头信息表 服务类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiHeaderService extends IService<ApiHeaderDO> {
    /**
     * @return
     */
    List<NewVO> getApi();
}
