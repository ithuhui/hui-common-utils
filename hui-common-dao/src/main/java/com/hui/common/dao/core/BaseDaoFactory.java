package com.hui.common.dao.core;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

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

    public static final String MYSQL_DRIVER = " com.mysql.jdbc.Driver";
    public static final String MYSQL_DRIVER_LATEST = " com.mysql.cj.jdbc.Driver";

    public static DataSource dataSourceInstance(String driverName) throws SQLException {
        // "jdbc:mysql://127.0.0.1:3306/db_user?useUnicode=true&characterEncoding=utf-8");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(MYSQL_DRIVER_LATEST);
        dataSource.setUrl("jdbc:mysql://127.0.0.1/hui_cloud_uc?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&&allowPublicKeyRetrieval=true");
        dataSource.setMaxActive(1);
        dataSource.setUsername("root");
        dataSource.setMaxActive(10);
        dataSource.setKeepAlive(true);
        dataSource.setPassword("123456");
        dataSource.setFailFast(true);
        dataSource.setConnectionErrorRetryAttempts(3);
        dataSource.setNotFullTimeoutRetryCount(3);
        return dataSource;
    }


}
