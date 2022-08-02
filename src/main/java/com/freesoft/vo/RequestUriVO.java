package com.freesoft.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zhouwei
 */
@Data
public class RequestUriVO {
    @NotBlank(message = "请求方法不能为空")
    private String method;
    @NotBlank(message = "请求路径不能为空")
    private String path;
    @NotBlank(message = "token不能为空")
    private String token;
    @ApiModelProperty(notes = "参数列表信息")
    private List params;
}
