package com.hui.common.dao.core;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * <code>IBaseDao</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/15 16:16.
 *
 * @author Gary.Hu
 */
public interface IBaseDao<Entity, PK> {
    Entity selectOne(Serializable id) throws SQLException;

    List<Entity> selectAll() throws SQLException;

    List<Entity> selectPage(int pageNum, int pageSize) throws SQLException;

    List<Entity> selectList() throws SQLException;

    int count() throws SQLException;

    PK insert(Entity entity) throws SQLException;

    int update(Entity entity) throws SQLException;

    int batchDelete(List<Serializable> ids) throws SQLException;

    int delete(Serializable id) throws SQLException;
}
