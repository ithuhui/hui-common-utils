package com.hui.common.dao.core.sql;

import java.util.List;
import java.util.Map;

/**
 * <code>SelectBuilder</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2019/12/18 17:24.
 *
 * @author Gary.Hu
 */
public class SelectBuilder {
    private static final String SELECT = "select %s from  `%s`  %s %s";
    private String tableName;
    private String fields;
    private String whereCondition;

    private StringBuilder resultSQL = new StringBuilder();

    public SelectBuilder(String tableName) {
        this.tableName = tableName;
        this.fields = "";
        this.whereCondition = "";
    }

    private SelectBuilder count() {
        this.fields = FunSQL.COUNT.getFunSQL("1");
        return this;
    }

    private SelectBuilder max(String field) {
        this.fields = FunSQL.MAX.getFunSQL(field);
        return this;
    }

    private SelectBuilder min(String field) {
        this.fields = FunSQL.MIN.getFunSQL(field);
        return this;
    }

    private SelectBuilder avg(String field) {
        this.fields = FunSQL.AVG.getFunSQL(field);
        return this;
    }

    private SelectBuilder sum(String field) {
        this.fields = FunSQL.SUM.getFunSQL(field);
        return this;
    }

    private SelectBuilder join(String tableName, String condition) {
        this.whereCondition = String.format("join `%s` on %s", tableName, condition);
        return this;
    }

    private SelectBuilder join(String tableName, String condition, String joinType) {
        this.whereCondition = String.format(" %s join `%s` on %s", joinType, tableName, condition);
        return this;
    }

    private SelectBuilder where(String key, String val) {
        return this;
    }

    private SelectBuilder where(String key, String compare, String val) {
        return this;
    }

    private SelectBuilder where(Map<String, String> whereMap) {
        return this;
    }

    private SelectBuilder or(String key) {
        return this;
    }

    private SelectBuilder or(String key, String val) {
        return this;
    }

    private SelectBuilder in(String... params) {
        return this;
    }

    private SelectBuilder notIn(String... params) {
        return this;
    }

    private SelectBuilder like(String key, String val) {
        return this;
    }

    private SelectBuilder groupBy(String... params){
        return this;
    }

    private String gen() {
        return String.format(SELECT, fields, tableName, whereCondition);
    }


    public static void main(String[] args) {

    }

}
