package com.hui.common.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * <code>BaseMapper</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 20:59.
 *
 * @author Gary.Hu
 */
public class BaseMapper<Entity, PK extends Serializable> implements IBaseDao<Entity, PK> {

    @Override
    public Entity selectOne(PK id) {
        return null;
    }

    @Override
    public List<Entity> selectList() {
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
    public int batchDelete(List<PK> ids) {
        return 0;
    }

    @Override
    public int delete(Serializable id) {
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
