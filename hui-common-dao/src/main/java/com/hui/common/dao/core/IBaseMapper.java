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

    List<Entity> selectAll() throws Exception;

    List<Entity> selectPage() throws SQLException;

    List<Entity> selectList()throws SQLException;

    int count()throws SQLException;

    Serializable insert(Entity entity)throws SQLException;

    List<Serializable> batchInsert(List<Entity> entityList)throws SQLException;

    int update(Entity entity)throws SQLException;

    List<Serializable> batchUpdate(List<Entity> entityList)throws SQLException;

    int batchDelete(List<Serializable> ids)throws SQLException;

    int delete(Serializable id)throws SQLException;

}
