package com.freesoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freesoft.mapper.ApiMainMapper;
import com.freesoft.mapper.ApiParameterMapper;
import com.freesoft.model.ApiMainDO;
import com.freesoft.model.ApiParameterDO;
import com.freesoft.service.ApiMainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 接口信息主类 服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@Service
public class ApiMainServiceImpl extends ServiceImpl<ApiMainMapper, ApiMainDO> implements ApiMainService {
    @Resource
    ApiMainMapper apiMainMapper;

    @Resource
    ApiParameterMapper apiParameterMapper;

    @Override
    public int saveApi(ApiMainDO apiMainDO) {
        Random random = new Random();
        int id = random.nextInt(10000);
        apiMainDO.setId(id);
        saveApiParams(id,apiMainDO);

        return apiMainMapper.insertInterface(apiMainDO);
    }

    @Override
    public void saveApiParams(Integer apiId,ApiMainDO apiMainDO) {
        //获取参数List数组
        List<ApiParameterDO> apiParameterList = apiMainDO.getParameters();
        for (int i = 0; i < apiParameterList.size(); i++) {
            apiParameterList.get(i).setApiId(apiId);
        }
        apiParameterMapper.saveApiParams(apiParameterList);
    }
}
