package com.hui.common.dao.core;

import com.hui.common.dao.constant.SqlConst;

import java.util.List;

/**
 * <code>CommonSqlRunner</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 21:28.
 *
 * @author Gary.Hu
 */
public class SqlGenerator {

    private static final String SELECT_SQL = "select %s from `%s` limit 1;";

    private static final String SELECT_COUNT_SQL = "select count(1) from `%s` %s %s";

    private static final String DELETE_SQL = "delete from `%s` %s %s";

    private static final String UPDATE_SQL = "update from";

    private String tableName;
    private String[] fields;
    private int offset = -1;
    private int size = -1;
    private boolean ignoreNullField;
    private String whereCondition;
    private List<Object> whereParams;

    private String limitStr;
    private String fieldStr;

    SqlGenerator(String tableName) {

        this.tableName = tableName;
    }

    public static String select(String tableName){
        return String.format(SELECT_SQL, SqlConst.ALL_FIELDS, tableName);
    }

}
