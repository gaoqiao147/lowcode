package com.freesoft.common.result;

import com.freesoft.common.enums.ResultStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhouwei
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(ResultStatusEnums resultStatus){
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public ResponseResult(ResultStatusEnums resultStatus, T data){
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }
}
