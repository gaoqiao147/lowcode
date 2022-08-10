package com.freesoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freesoft.mapper.*;
import com.freesoft.model.*;
import com.freesoft.service.ApiMainService;
import com.freesoft.utils.ApiUtil;
import com.freesoft.utils.DataSourceNode;
import com.freesoft.utils.DataSourceUtil;
import com.freesoft.vo.DataTypeVO;
import com.freesoft.vo.ParamsVO;
import com.freesoft.vo.RequestParamsVO;
import com.freesoft.vo.RequestUriVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
    //spring的@Lazy注解。在spring容器启动的时候，不会调用其getBean方法初始化实例。
    @Lazy
    @Resource
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
        //保存参数(输出)
        saveApiParams(id, apiMainDO);
        //保存参数(输入)
        saveApiParamsOut(id, apiMainDO);
        //保存主接口信息
        int res = apiMainMapper.insertInterface(apiMainDO);
        //对接口上架
        apiUtil.registerApi(apiMainDO);
        return res;
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
    public void saveApiParamsOut(Integer apiId, ApiMainDO apiMainDO) {
        //获取参数List数组
        List<ApiParams> apiParameterList = apiMainDO.getParametersOut();
        for (int i = 0; i < apiParameterList.size(); i++) {
            apiParameterList.get(i).setApiId(apiId);
        }
        apiParameterMapper.saveApiParamsOut(apiParameterList);
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
                //获取参数信息（要输入的参数，用户填的信息）
                List<ParamsVO> paramsVOList = apiParameterMapper.paramsList(apiMain.getId());
                //用户要输出的参数
                List<ParamsVO> paramsVOList2 = apiParameterMapper.paramsList2(apiMain.getId());
                //参数替换sql脚本
                String sql = apiMain.getExecuteSql();
                //针对该数据源执行sql脚本
                if (null != paramsVOList) {
                    result = execute(dataSourceNode, sql, paramsVOList, paramsVOList2, parameters);
                } else {
                    result = execute(dataSourceNode, sql);
                }
                System.out.println(result);
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
     */
    private Object execute(DataSourceNode dataSourceNode, String executeSql, List<ParamsVO> paramsVOList, List<ParamsVO> paramsVOList2, Map<String, Object> parameters) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();
        try {
            connection = dataSourceNode.getDataSource().getConnection();
            ps = connection.prepareStatement(executeSql);
            //占位符个数不确定，所以不能直接.所以需要对是否有占位符有几个进行判断
            //如果有拼接占位符号？，则在循环中对占位符进行赋值
            for (int i = 0; i < parameters.size(); i++) {
                ps.setInt(i + 1, (Integer) parameters.get(paramsVOList.get(i).getParams()));
            }
            rs = ps.executeQuery();
            //用于循环得到list2数组的值
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < paramsVOList2.size(); j++) {
                    map.put(paramsVOList2.get(j).getParams(), rs.getString(paramsVOList2.get(j).getParams()));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            //ResultSet，PreparedStatement，Connection对象，使用完之后，调用close方法关闭资源
            if (null != rs) {
                rs.close();
            }
            if (null != ps) {
                ps.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
        return map;
    }

    private Object execute(DataSourceNode dataSourceNode, String executeSql) throws SQLException {
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
