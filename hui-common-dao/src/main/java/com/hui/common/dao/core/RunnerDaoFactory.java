package com.hui.common.dao.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import com.google.gson.annotations.SerializedName;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <code>DaoFactory</code>
 * <desc>
 * 描述： BaseDao构建工厂
 * <desc/>
 * Creation Time: 2019/12/8 16:34.
 *
 * @author Gary.Hu
 */
public class RunnerDaoFactory {

    public static final String MYSQL_DRIVER = " com.mysql.jdbc.Driver";
    public static final String MYSQL_DRIVER_LATEST = " com.mysql.cj.jdbc.Driver";


    public static RunnerDao createRunnerDao(String driverName, String url, String username, String password) throws SQLException {
        return createRunnerDao(driverName, url, username, password, null);
    }

    public static RunnerDao createRunnerDao(String driverName, String url, String username, String password, Properties properties) throws SQLException {
        DataSource dataSource = dataSourceInstance(driverName, url, username, password);
        if (null != properties) {
            // TODO readme.md 提供 druid线程池的常用属性设置
            invokeByProperties(dataSource, properties);
        }
        return new RunnerDao(dataSource);
    }

    private static void invokeByProperties(DataSource dataSource, Properties properties) {
        // TODO 反射执行 datasource
    }

    private static DataSource dataSourceInstance(String driverName, String url, String username, String password) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setMaxActive(1);
        dataSource.setUsername(username);
        dataSource.setMaxActive(10);
        dataSource.setKeepAlive(true);
        dataSource.setPassword(password);
        dataSource.setFailFast(true);
        dataSource.setConnectionErrorRetryAttempts(3);
        dataSource.setNotFullTimeoutRetryCount(3);
        return dataSource;
    }
}
