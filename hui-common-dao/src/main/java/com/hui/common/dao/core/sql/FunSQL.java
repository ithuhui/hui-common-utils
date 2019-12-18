package com.hui.common.dao.core.sql;

/**
 * <code>FunHandler</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2019/12/18 17:33.
 *
 * @author Gary.Hu
 */
public enum FunSQL {
    /**
     * BASE SQL FUNCTION
     */
    COUNT("count(%s) as count"),
    MAX("max(%s) as avg"),
    MIN("min(%s) as min"),
    AVG("avg(%s) as avg"),
    SUM("sum(%s) as sum");

    FunSQL(String funContent) {
        this.funContent = funContent;
    }

    private String funContent;


    public String getFunSQL(String... params){
        return String.format(this.funContent,params);
    }
}
