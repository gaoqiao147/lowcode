package com.freesoft.service.impl;

import com.freesoft.model.ApiHeaderDO;
import com.freesoft.mapper.ApiHeaderMapper;
import com.freesoft.service.ApiHeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freesoft.vo.NewVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 请求头信息表 服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Service
public class ApiHeaderServiceImpl extends ServiceImpl<ApiHeaderMapper, ApiHeaderDO> implements ApiHeaderService {
    @Resource
    ApiHeaderMapper apiHeaderMapper;
    @Override
    public List<NewVO> getApi() {
        return apiHeaderMapper.getAllByApiIdNewVos();
    }
}
