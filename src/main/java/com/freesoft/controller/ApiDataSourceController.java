package com.freesoft.controller;

import com.freesoft.common.method.GenPathMethods;
import com.freesoft.common.method.SplicingSqlMethods;
import com.freesoft.model.ApiMainDO;
import com.freesoft.model.ApiParameterDO;
import com.freesoft.model.ApiParams;
import com.freesoft.service.ApiDataSourceService;
import com.freesoft.service.ApiMainService;
import com.freesoft.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 数据源配置表 前端控制器
 * </p>
 *
 * @author zhouwei
 * @since 2022-07-26
 */
@RestController
@RequestMapping("/api-data-source")
public class ApiDataSourceController {
    @Resource
    ApiDataSourceService apiDataSourceService;
    @Resource
    ApiMainService apiMainService;
    @Resource
    SplicingSqlMethods splicingSqlMethods;
    @Resource
    GenPathMethods genPathMethods;

    @GetMapping("/get-table-name")
    public List<TableNameVO> getTableName() {
        return apiDataSourceService.getTableName();
    }

    @GetMapping("/get-column-name")
    public List<ColumnNameVO> getColumnName(@RequestParam String tableName) {
        return apiDataSourceService.getColumnName(tableName);
    }

    @PostMapping("/gen-api")
    public Object genApi(@RequestBody GenerateVO generateVO) {
        //随机生成id
        int id = new Random().nextInt(10000);
        //生成path地址
        String str_id = ""+id;
        String path = genPathMethods.genPath(generateVO.getTableName(),str_id).toString();
        //获取参数名
        List<ParamsVO> paramsVOListIn = generateVO.getInputParamsList();
        //获取参数类型
        List<DataTypeVO> dataTypeListIn = generateVO.getInputDataType();

        //获取参数名
        List<ParamsVO> paramsVOListOut = generateVO.getOutputParamsList();
        //获取参数类型
        List<DataTypeVO> dataTypeListOut = generateVO.getOutputDataType();

        //判断参数是否为空
        //循环添加参数入listIn数组
        List<ApiParameterDO> listIn = new ArrayList<>();
        if (!dataTypeListIn.isEmpty() && !paramsVOListIn.isEmpty()) {
            for (int i = 0; i < paramsVOListIn.size(); i++) {
                ApiParameterDO apiParameterDO = new ApiParameterDO()
                        .setKey(paramsVOListIn.get(i).getParams())
                        .setDataType(dataTypeListIn.get(i).getParamsDataType())
                        .setApiId(Integer.valueOf(str_id));
                listIn.add(apiParameterDO);
            }
        }
        //循环添加参数入list数组
        List<ApiParams> listOut = new ArrayList<>();
        if (!dataTypeListOut.isEmpty() && !paramsVOListOut.isEmpty()) {
            for (int i = 0; i < paramsVOListOut.size(); i++) {
                ApiParams apiParams = new ApiParams()
                        .setKey(paramsVOListOut.get(i).getParams())
                        .setDataType(dataTypeListOut.get(i).getParamsDataType())
                        .setApiId(Integer.valueOf(str_id));
                listOut.add(apiParams);
            }
        }

        ApiMainDO apiMain = new ApiMainDO()
                .setId(Integer.valueOf(str_id))
                .setExecuteSql(splicingSqlMethods.splicingSql(generateVO).toString())
                .setMethod(generateVO.getMethod())
                .setPath(path)
                .setParameters(listIn)
                .setParametersOut(listOut);
        int res = apiMainService.saveApi(apiMain);
        return res;
    }
}

