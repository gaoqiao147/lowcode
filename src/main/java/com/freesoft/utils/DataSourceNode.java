package com.freesoft.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.Closeable;

/**
 * 数据源节点
 *
 * @author mingHang
 * @date 2022-02-28 11:25
 */
@Slf4j
@Getter
@Setter
public class DataSourceNode {
    private String id;

    private String name;

    private DataSourceTransactionManager dataSourceTransactionManager;

    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    DataSourceNode(DataSource dataSource, String name, String id, int maxRows) {
        this.dataSource = dataSource;
        this.name = name;
        this.id = id;
        this.dataSourceTransactionManager = new DataSourceTransactionManager(this.dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcTemplate.setMaxRows(maxRows);
    }

    public void close() {
        if (this.dataSource != null) {
            if (this.dataSource instanceof Closeable) {
                try {
                    ((Closeable) this.dataSource).close();
                } catch (Exception e) {
                    log.warn("Close DataSource error", e);
                }
            } else {
                log.warn("DataSource can not close");
            }
        }
    }
}
