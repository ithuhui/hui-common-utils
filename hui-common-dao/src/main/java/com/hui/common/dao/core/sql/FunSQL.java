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
    COUNT("select count(%s) as count "),
    MAX("select max(%s) as avg "),
    MIN("select min(%s) as min "),
    AVG("select avg(%s) as avg "),
    SUM("select sum(%s) as sum ");

    FunSQL(String funContent) {
        this.funContent = funContent;
    }

    private String funContent;


    public String getFunSQL(String param){
        return String.format(this.funContent,param);
    }
}
