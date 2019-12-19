package com.hui.common.dao.core;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <code>MySqlDao</code>
 * <desc>
 * 描述： 针对单表对应数据库的底层封装 (不同的数据库 部分sql语法不一致)
 * <desc/>
 * Creation Time: 2019/12/14 12:07.
 *
 * @author Gary.Hu
 */
public class MySqlDao<PK extends Serializable> extends BaseDao<PK> {
    public MySqlDao(RunnerDao runnerDao, String tableName, String primaryKey) {
        super(runnerDao, tableName, primaryKey);
    }

    @Override
    public List<Serializable> batchInsert(List<Map<String, String>> maps) throws SQLException {
        return null;
    }

    @Override
    public int batchUpdate(List<Map<String, String>> entities) throws SQLException {
        return 0;
    }

}
