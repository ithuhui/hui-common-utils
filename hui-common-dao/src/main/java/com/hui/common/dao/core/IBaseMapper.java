package com.hui.common.dao.core;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * <code>IBaseMapper</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/11 1:28.
 *
 * @author Gary.Hu
 */
public interface IBaseMapper<Entity, PK extends Serializable> {
    Entity selectOne(Serializable id) throws SQLException;

    List<Entity> selectAll();

    List<Entity> selectPage();

    List<Entity> selectList();

    int count();

    Serializable insert(Entity entity);

    List<Serializable> batchInsert(List<Entity> entityList);

    int update(Entity entity);

    List<Serializable> batchUpdate(List<Entity> entityList);

    int batchDelete(List<Serializable> ids);

    int delete(Serializable id);

}
