package com.hui.common.dao.core;

import org.apache.commons.dbutils.ResultSetHandler;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <code>CommonMapper</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 0:37.
 *
 * @author Gary.Hu
 */
public class MysqlBaseDao<Entity, PK extends Serializable> implements IBaseDao<Map<String, Object>, PK> {

    private CommonQueryRunner queryRunner;
    private String tableName;

    @Override
    public Map<String, Object> selectOne(PK id) throws SQLException {
        String sql = SqlGenerator.select(tableName);
        queryRunner.query(sql, new ResultSetHandler<Map<String,Object>>() {
            @Override
            public Map<String, Object> handle(ResultSet rs) throws SQLException {
                while (rs.next()){
                    int columnCount = rs.getMetaData().getColumnCount();
                }
                return null;
            }
        });
        return null;
    }

    @Override
    public List<Map<String, Object>> selectList() {
        return null;
    }

    @Override
    public PK insert() {
        return null;
    }

    @Override
    public List<PK> batchInsert() {
        return null;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public List<PK> batchUpdate() {
        return null;
    }

    @Override
    public int delete(PK id) {
        return 0;
    }

    @Override
    public int batchDelete(List<PK> ids) {
        return 0;
    }

    @Override
    public int execute(String sql, Object... params) {
        return 0;
    }

    @Override
    public int execute(String sql) {
        return 0;
    }
}
