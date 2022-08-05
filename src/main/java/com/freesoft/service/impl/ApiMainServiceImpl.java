package com.freesoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freesoft.mapper.*;
import com.freesoft.model.*;
import com.freesoft.service.ApiMainService;
import com.freesoft.utils.ApiUtil;
import com.freesoft.utils.DataSourceNode;
import com.freesoft.utils.DataSourceUtil;
import com.freesoft.vo.RequestParamsVO;
import com.freesoft.vo.RequestUriVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @Resource
    ApiGroupMapper apiGroupMapper;
    @Resource
    ApiDataSourceMapper apiDataSourceMapper;
    @Resource
    ApiMainGroupMapper apiMainGroupMapper;
    @Lazy
    @Autowired
    ApiUtil apiUtil;


    /**
     * 项目启动时，对所有接口进行上架
     */
    @PostConstruct
    void init() {
        List<ApiMainDO> apiMains = apiMainMapper.getAll();
        for (ApiMainDO apiMain : apiMains) {
            apiUtil.registerApi(apiMain);
        }
    }

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
                    listKey.add(listParams.get(i + 1).getKey());
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

    @Override
    public Object invoke(HttpServletRequest request, Map<String, Object> headers, Map<String, Object> parameters) {
        String method = request.getRequestURI();
        //去除请求地址中的 "/open"
        String url = request.getRequestURI().replace("/open", "");
        Object result = null;
        boolean isPass = false;
        try {
            ApiMainDO apiMain = new ApiMainDO();
            ApiMainDO apiMain2 = apiMainMapper.getOneByPath(url);
            apiMain.setMethod(apiMain2.getMethod());
            apiMain.setPath(apiMain2.getPath());
            apiMain.setId(apiMain2.getId());
            apiMain.setEnable(apiMain2.getEnable().toString());
            apiMain.setExecuteSql(apiMain2.getExecuteSql());
            if ("0".equals(apiMain.getEnable())) {
                //启用数据源 通过groupId查询分组信息
                ApiMainGroupDO apiMainGroupDO = apiMainGroupMapper.selectById(apiMain.getId());
                ApiGroupDO apiGroup = apiGroupMapper.selectById(apiMainGroupDO.getGroupId());
                //通过分组信息获取数据源
                DataSourceNode dataSourceNode = queryDataSource(apiGroup.getDataSourceId());
                //参数替换sql脚本
                String sql = apiMain.getExecuteSql();
                //针对该数据源执行sql脚本
                result = execute(dataSourceNode, sql);
                isPass = true;
                return result;
            } else if ("1".equals(apiMain.getEnable())) {
                //不启用数据源
                String serverName = "";
                return null;
            }
        } catch (Exception e) {
            result = e.getMessage();
            log.error("接口请求异常：{}", e);
            return null;
        } finally {
        }
        return null;
    }

    /**
     * 执行方法
     *
     * @param executeSql
     * @return java.lang.Object
     * @author mingHang
     * @date 2022/2/28 13:52
     */
    private Object execute(DataSourceNode dataSourceNode, String executeSql) {
        return dataSourceNode.getJdbcTemplate().queryForList(executeSql);
    }

    /**
     * 根据数据源id获取数据源
     *
     * @param id
     * @return com.freesofts.framework.api.util.DataSourceNode
     * @author mingHang
     * @date 2022/2/28 14:22
     */
    private DataSourceNode queryDataSource(String id) {
        if (DataSourceUtil.dynamicDataSource.containsKey(id)) {
            return DataSourceUtil.dynamicDataSource.get(id);
        }
        //不存在
        ApiDataSourceDO apiDataSource = apiDataSourceMapper.selectById(id);
        return DataSourceUtil.createDataSourceNode(apiDataSource);
    }
}
