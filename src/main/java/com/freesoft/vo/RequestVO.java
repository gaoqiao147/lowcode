package com.freesoft.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zhouwei
 */
@Data
public class RequestVO {
    @NotBlank(message = "请求方法不能为空")
    private String method;
    @NotBlank(message = "请求路径不能为空")
    private String path;
    @NotBlank(message = "token不能为空")
    private String token;
    @NotBlank(message = "请求人姓名不能为空")
    private String createdName;

    private List<ParamsVO> params;
}
