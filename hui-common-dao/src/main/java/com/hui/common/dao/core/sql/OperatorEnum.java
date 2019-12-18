package com.hui.common.dao.core.sql;

/**
 * <code>OperatorEnum</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2019/12/18 18:02.
 *
 * @author Gary.Hu
 */
public enum OperatorEnum {
    /**
     * 基础运算符
     */
    GT(" > "),
    LT(" < "),
    EQ(" = "),
    NE(" != "),
    GE(" >= "),
    LE(" <= ");

    OperatorEnum(String operator) {
        this.operator = operator;
    }

    private String operator;
}
