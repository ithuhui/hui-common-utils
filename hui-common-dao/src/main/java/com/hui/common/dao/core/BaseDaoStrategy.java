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
     * INIT SINGLE
     */
    MYSQL {
        @Override
        public BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
            return new MySqlDao(runnerDao, tableName, primaryKey);
        }
    },
    ORACLE {
        @Override
        public BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
            return null;
        }
    },
    POSTGRESQL {
        @Override
        public BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
            return null;
        }
    };

    public abstract BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey);
}
