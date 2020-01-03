package com.hui.common.dao.core;

import com.alibaba.druid.DruidRuntimeException;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import com.google.gson.annotations.SerializedName;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Enumeration;
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

    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_DRIVER_LATEST = "com.mysql.cj.jdbc.Driver";


    public static RunnerDao createRunnerDao(String driverName, String url, String username, String password) throws SQLException {
        return createRunnerDao(driverName, url, username, password, null);
    }

    public static RunnerDao createRunnerDao(String driverName, String url, String username, String password, Properties properties) throws SQLException {
        DataSource dataSource = dataSourceInstance(driverName, url, username, password);
        if (null != properties) {
            // TODO readme.md 提供 druid线程池的常用属性设置
            try {
                invokeByProperties(dataSource, properties);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new RunnerDao(dataSource);
    }

    private static void invokeByProperties(DataSource dataSource, Properties properties) throws InvocationTargetException, IllegalAccessException {
        // 获取所有设置的Method
        Method[] methods = dataSource.getClass().getMethods();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement().toString();
            String val = properties.getProperty(key);
            String methodName = "set" + key;
            Method method = findMethod(methods, methodName);
            if (null != method && method.getParameterCount() != 1) {
                throw new DruidRuntimeException("No method to execute by [" + methodName + "]");
            }
            Class<?> parameterType = method.getParameterTypes()[0];
            method.invoke(dataSource, getParameters(val, parameterType));
        }

    }

    private static <T> T getParameters(String value, Class<?> parameterType) {
        Object parameter = new Object();
        if (int.class.equals(parameterType)) {
            parameter = Integer.valueOf(value);
        }
        if (String.class.equals(parameterType)) {
            parameter = value;
        }
        if (boolean.class.equals(parameterType)) {
            parameter = Boolean.valueOf(value);
        }
        return (T) parameter;
    }

    private static Method findMethod(Method[] methods, String methodName) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private static DataSource dataSourceInstance(String driverName, String url, String username, String password) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setMaxActive(5);
        dataSource.setUsername(username);
        dataSource.setMaxActive(30);
        dataSource.setKeepAlive(true);
        dataSource.setPassword(password);
        dataSource.setFailFast(true);
        dataSource.setConnectionErrorRetryAttempts(3);
        dataSource.setNotFullTimeoutRetryCount(3);
        return dataSource;
    }
}
