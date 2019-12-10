package com.hui.common.dao.core;

/**
 * <code>BaseDaoStrategy</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/10 1:17.
 *
 * @author Gary.Hu
 */
public enum BaseDaoStrategy {
    /**
     * baseDao init
     */
    MY_SQL {
        @Override
        IBaseDao createBaseDao() {
            return new MysqlBaseDao();
        }
    },
    ORACLE {
        @Override
        IBaseDao createBaseDao() {
            return null;
        }
    };
    private IBaseDao baseDao;

    abstract IBaseDao createBaseDao();
}
