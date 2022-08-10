package com.freesoft.service;

import com.freesoft.model.ApiMainDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freesoft.model.ApiParameterDO;
import com.freesoft.vo.RequestUriVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口信息主类 服务类
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
public interface ApiMainService extends IService<ApiMainDO> {
    /**
     * 保存接口
     * @param apiMainDO
     * @return
     */
    int saveApi(ApiMainDO apiMainDO);

    /**
     * 保存接口参数
     * @param apiId
     * @param apiMainDO
     */
    void saveApiParams(Integer apiId,ApiMainDO apiMainDO);

    /**
     * 保存接口参数
     * @param apiId
     * @param apiMainDO
     */
    void saveApiParamsOut(Integer apiId,ApiMainDO apiMainDO);

    /**
     * 得到所有的可访问的接口地址
     *
     * @return
     */
    List<RequestUriVO> getAllApi();

    /**
     * 自定义接口实现
     *
     * @param request
     * @param headers
     * @param parameters
     * @return java.lang.Object
     * @author mingHang
     * @date 2022/2/28 14:44
     */
    Object invoke(HttpServletRequest request, Map<String, Object> headers, Map<String, Object> parameters);
}
