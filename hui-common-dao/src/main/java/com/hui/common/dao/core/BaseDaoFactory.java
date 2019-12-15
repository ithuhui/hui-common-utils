package com.hui.common.dao.core;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>DaoFactory</code>
 * <desc>
 * 描述： BaseDao构建工厂
 * <desc/>
 * Creation Time: 2019/12/8 16:34.
 *
 * @author Gary.Hu
 */
public class BaseDaoFactory {

    private Map<String, DataSource> multiDataSourceMap = new HashMap<>();

    public static final String MYSQL_DRIVER = " com.mysql.jdbc.Driver";
    public static final String MYSQL_DRIVER_LATEST = " com.mysql.cj.jdbc.Driver";

    public static DataSource dataSourceInstance(String driverName, String url, String username, String password) throws SQLException {
        // jdbc:mysql://127.0.0.1/hui_cloud_uc?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&&allowPublicKeyRetrieval=true
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

    public static BaseDao createBaseDao(RunnerDao runnerDao,String tableName,String primaryKey) throws SQLException {
        BaseDaoStrategy strategy = BaseDaoStrategy.valueOf("MYSQL");
        BaseDao baseDao = strategy.createBaseDao(runnerDao, tableName, primaryKey);
        return baseDao;
    }

    public static RunnerDao createRunnerDao(String driverName, String url, String username, String password) throws SQLException {
        DataSource dataSource = dataSourceInstance(driverName, url, username, password);
        return new RunnerDao(dataSource);
    }
    private void invokeByProperties(DataSource dataSource) {

    }

}
