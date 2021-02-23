package pers.hui.common.dao.core;

import java.io.Serializable;

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
        public <PK extends Serializable> BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
            return new MySqlDao<PK>(runnerDao, tableName, primaryKey);
        }
    },
    ORACLE {
        @Override
        public <PK extends Serializable> BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
            return null;
        }
    },
    POSTGRESQL {
        @Override
        public <PK extends Serializable> BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
            return null;
        }
    };

    public abstract <PK extends Serializable> BaseDao createBaseDao(RunnerDao runnerDao, String tableName, String primaryKey);
}
