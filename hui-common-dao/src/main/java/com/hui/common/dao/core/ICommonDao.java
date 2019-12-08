package com.hui.common.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * <code>MysqlCommonDao</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/8 16:26.
 *
 * @author Gary.Hu
 */
public interface ICommonDao<Entity> {
    Entity selectOne(Serializable id);

    List<Entity> selectList();

    int insert(Entity entity);

    int update(Entity entity);

    int delete(Serializable id);

    int execute(String sql);

    int execute(String sql, Object... params);
}
