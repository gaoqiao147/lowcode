package com.freesoft.common.method;

import org.springframework.stereotype.Service;

/**
 * @author zhouwei
 */
@Service
public class GenPathMethods {
    final static String HTTP = "http://";

    public StringBuilder genPath(String tableName, String id) {
        /**
         *   http:// ip地址 ： 端口号 / tableName / 接口id
         */
        StringBuilder sbPath = new StringBuilder();
        sbPath.append(HTTP).append("127.0.0.1:8081/").append(tableName).append("/").append(id);
        return sbPath;
    }
}
