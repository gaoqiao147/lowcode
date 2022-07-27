package com.freesoft.common.enums;

/**
 * @author zhouwei
 */
public enum ResultStatusEnums {

    SUCCESS(200,"成功"),
    FAIL(400,"失败");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
