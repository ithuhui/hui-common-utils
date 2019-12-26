package com.hui.common.model.constant;

/**
 * <code>ExceptionEnum</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/18 1:23.
 *
 * @author Gary.Hu
 */
public enum  ExceptionEnum {
    ;

    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;
}
