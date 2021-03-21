package pers.hui.common.beetl.model;

/**
 * <code>SqlKey</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/21 0:36.
 *
 * @author Ken.Hu
 */
public enum SqlKey {

    /**
     * Include基础模板/变量模板
     */
    INCLUDE,
    DIM,
    KPI,
    WHERE,
    GROUP_BY,
    ;

    @Override
    public String toString() {
        return this.name();
    }
}
