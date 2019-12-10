package com.hui.common.dao.core;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * <code>IBaseMapper</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 20:59.
 *
 * @author Gary.Hu
 */
public interface IBaseDao<Entity, PK extends Serializable> {
    Entity selectOne(PK id) throws SQLException;

    List<Entity> selectList();

    PK insert();

    List<PK> batchInsert();

    int update();

    List<PK> batchUpdate();

    int delete(PK id);

    int batchDelete(List<PK> ids);

    int execute(String sql, Object... params);

    int execute(String sql);

}
