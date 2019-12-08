package com.hui.common.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * <code>CommonMapper</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 0:37.
 *
 * @author Gary.Hu
 */
public class CommonMapper<Entity> implements ICommonDao<Entity>{


    @Override
    public Entity selectOne(Serializable id) {
        return null;
    }

    @Override
    public List<Entity> selectList() {
        return null;
    }

    @Override
    public int insert(Entity entity) {
        return 0;
    }

    @Override
    public int update(Entity entity) {
        return 0;
    }

    @Override
    public int delete(Serializable id) {
        return 0;
    }

    @Override
    public int execute(String sql) {
        return 0;
    }

    @Override
    public int execute(String sql, Object... params) {
        return 0;
    }
}
