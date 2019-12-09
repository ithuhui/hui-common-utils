package com.hui.common.dao.core;

import java.io.Serializable;
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
    public Map<String, Object> selectOne(PK id) {
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
