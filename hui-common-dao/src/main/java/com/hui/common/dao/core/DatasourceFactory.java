package com.hui.common.dao.core;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <code>DaoFactory</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/8 16:34.
 *
 * @author Gary.Hu
 */
public class DatasourceFactory {

    public static final String MYSQL_DRIVER = " com.mysql.jdbc.Driver";
    public static final String MYSQL_DRIVER_LATEST = " com.mysql.cj.jdbc.Driver";

    public static DataSource dataSourceInstance(String driverName) {
        // "jdbc:mysql://127.0.0.1:3306/db_user?useUnicode=true&characterEncoding=utf-8");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl("");
        dataSource.setMaxActive(1);
        dataSource.setUsername("username");
        dataSource.setMaxActive(10);
        dataSource.setKeepAlive(true);
        dataSource.setPassword("password");
        dataSource.setConnectionErrorRetryAttempts(3);
        dataSource.setNotFullTimeoutRetryCount(3);
        return dataSource;
    }



}
