package com.freesoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freesoft.mapper.ApiMainMapper;
import com.freesoft.mapper.ApiParameterMapper;
import com.freesoft.model.ApiMainDO;
import com.freesoft.model.ApiParameterDO;
import com.freesoft.service.ApiMainService;
import com.freesoft.vo.RequestParamsVO;
import com.freesoft.vo.RequestUriVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
        saveApiParams(id, apiMainDO);
        return apiMainMapper.insertInterface(apiMainDO);
    }

    @Override
    public void saveApiParams(Integer apiId, ApiMainDO apiMainDO) {
        //获取参数List数组
        List<ApiParameterDO> apiParameterList = apiMainDO.getParameters();
        for (int i = 0; i < apiParameterList.size(); i++) {
            apiParameterList.get(i).setApiId(apiId);
        }
        apiParameterMapper.saveApiParams(apiParameterList);
    }

    @Override
    public List<RequestUriVO> getAllApi() {
        List<RequestUriVO> list = new ArrayList<>();
        List<String> listKey = new ArrayList<>();
        RequestUriVO requestUriVO = new RequestUriVO();
        List<RequestParamsVO> listParams = apiMainMapper.getAllApi();
        for (int i = 0; i < listParams.size(); i++) {
            if (i < listParams.size() - 1) {
                if (listParams.get(i).getApiId().equals(listParams.get(i + 1).getApiId())) {
                    listKey.add(listParams.get(i).getKey());
                    listKey.add(listParams.get(i+1).getKey());
                }
                if (!listParams.get(i).getApiId().equals(listParams.get(i + 1).getApiId()) || (i == listParams.size() - 2)) {
                    requestUriVO.setMethod(listParams.get(i).getMethod());
                    requestUriVO.setPath(listParams.get(i).getPath());
                    requestUriVO.setParams(listKey);
                    list.add(requestUriVO);
                }
            }
        }
        LinkedHashSet<RequestUriVO> hashSet = new LinkedHashSet<>(list);
        ArrayList<RequestUriVO> listWithoutDuplicates = new ArrayList<RequestUriVO>(hashSet);
        return listWithoutDuplicates;
    }
}
