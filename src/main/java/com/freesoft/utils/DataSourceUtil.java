package com.freesoft.utils;

import com.freesoft.controller.ApiDataSourceController;
import com.freesoft.model.ApiDataSourceDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.datasource.SmartDataSource;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源操作
 *
 * @author mingHang
 * @date 2022-02-28 11:16
 */
@Slf4j
public class DataSourceUtil {
    /**
     * 线程安全
     */
    public static Map<String, DataSourceNode> dynamicDataSource = new ConcurrentHashMap<>();

    /**
     * 连接池工具
     *
     * @author mingHang
     * @date 2022/3/2 10:06
     * @param null
     * @return null
     */
    private static final String SOURCE_TYPE = "com.zaxxer.hikari.HikariDataSource";

    /**
     * 创建数据源对象
     *
     * @return javax.sql.DataSource
     * @author mingHang
     * @date 2022/2/28 10:25
     */
    public static DataSourceNode createDataSourceNode(ApiDataSourceDO apiDataSource) {
        DataSourceNode dataSourceNode = new DataSourceNode(createDataSource(apiDataSource),
                apiDataSource.getName(), apiDataSource.getId(), -1);
        dynamicDataSource.put(apiDataSource.getId(), dataSourceNode);
        return dataSourceNode;
    }

    /**
     * 创建数据源
     *
     * @return javax.sql.DataSource
     * @author mingHang
     * @date 2022/3/2 10:00
     */
    private static DataSource createDataSource(ApiDataSourceDO apiDataSource) {
        Class<? extends DataSource> dataSourceType = getDataSourceType(SOURCE_TYPE);
        String driverClass = DatabaseDriver.fromJdbcUrl(apiDataSource.getUrl()).getDriverClassName();
        DataSource dataSource = BeanUtils.instantiateClass(dataSourceType);
        Map<String, Object> map = new HashMap<>(6);
        map.put("driverClassName", driverClass);
        map.put("name", apiDataSource.getName());
        map.put("url", apiDataSource.getUrl());
        map.put("type", SOURCE_TYPE);
        map.put("username", apiDataSource.getUsername());
        map.put("password", apiDataSource.getPassword());
        ConfigurationPropertySource source = new MapConfigurationPropertySource(map);
        ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
        aliases.addAliases("url", "jdbc-url");
        aliases.addAliases("username", "user");
        Binder binder = new Binder(source.withAliases(aliases));
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(dataSource));
        return dataSource;

    }

    /**
     * 删除数据源
     *
     * @param apiDataSource
     * @author mingHang
     * @date 2022/3/1 20:22
     */
    public static void deleteDataSourceNode(ApiDataSourceDO apiDataSource) {
        deleteDataSourceNode(apiDataSource.getId());
    }

    /**
     * 删除数据源
     *
     * @param id
     * @author mingHang
     * @date 2022/4/6 16:21
     */
    public static void deleteDataSourceNode(String id) {
        DataSourceNode dataSourceNode = dynamicDataSource.remove(id);
        if (null != dataSourceNode) {
            dataSourceNode.close();
        }
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends DataSource> getDataSourceType(String datasourceType) {
        try {
            return (Class<? extends DataSource>) ClassUtils.forName(datasourceType, ApiDataSourceController.class.getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException("not found:" + datasourceType);
        }
    }

    /**
     * 测试连接
     *
     * @param apiDataSource
     * @author mingHang
     * @date 2022/3/2 10:10
     */
    public static int test(ApiDataSourceDO apiDataSource) {
        DataSource dataSource = createDataSource(apiDataSource);
        try {
            Connection connection = dataSource.getConnection();
            doCloseConnection(connection, dataSource);
            return 1;
        } catch (SQLException e) {
            log.error("测试数据源连接失败", e);
        } finally {
            closeDataSource(dataSource);
        }
        return 0;
    }

    /**
     * 关闭连接
     *
     * @param connection
     * @param dataSource
     * @author mingHang
     * @date 2022/3/2 10:09
     */
    private static void doCloseConnection(Connection connection, DataSource dataSource) throws SQLException {
        if (!(dataSource instanceof SmartDataSource) || ((SmartDataSource) dataSource).shouldClose(connection)) {
            connection.close();
        }
    }

    /**
     * 关闭数据源
     *
     * @param dataSource
     * @author mingHang
     * @date 2022/3/2 10:09
     */
    private static void closeDataSource(DataSource dataSource) {
        if (dataSource != null) {
            if (dataSource instanceof Closeable) {
                try {
                    ((Closeable) dataSource).close();
                } catch (Exception e) {
                    log.warn("Close DataSource error", e);
                }
            } else {
                log.warn("DataSource can not close");
            }
        }
    }
}
