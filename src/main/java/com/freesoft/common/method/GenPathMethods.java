package com.freesoft.common.method;

import org.springframework.stereotype.Service;

/**
 * @author zhouwei
 */
@Service
public class GenPathMethods {

    public StringBuilder genPath(String tableName, String id) {
        /**
         * tableName / 接口id
         */
        StringBuilder sbPath = new StringBuilder();
        sbPath.append("/").append(tableName).append("/").append(id);
        return sbPath;
    }
}
