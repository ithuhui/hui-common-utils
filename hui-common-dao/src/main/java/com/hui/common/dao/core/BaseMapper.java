package com.hui.common.dao.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hui.common.dao.utils.GsonUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * <code>BaseMapper</code>
 * <desc>
 * 描述： 只做一个操作,map转json转entity
 * <desc/>
 * Creation Time: 2019/12/9 20:59.
 *
 * @author Gary.Hu
 */
public class BaseMapper<Entity, PK extends Serializable> implements IBaseMapper<Entity, PK> {

    private BaseDao baseDao;
    private Class<Entity> clazz;

    private Gson gson = GsonUtils.INSTANCE.getGson();

    @Override
    public Entity selectOne(Serializable id) throws SQLException {
        Object object = baseDao.selectOne(id);
        String json = gson.toJson(object, String.class);
        Entity entity = gson.fromJson(json, clazz);
        return entity;
    }

    @Override
    public List<Entity> selectAll() throws SQLException {
        List list = baseDao.selectAll();
        String json = gson.toJson(list, String.class);
        List<Entity> entityList = gson.fromJson(json, new TypeToken<List<Entity>>() {
        }.getType());
        return entityList;
    }

    @Override
    public List<Entity> selectPage() throws SQLException {
        List list = baseDao.selectAll();
        String json = gson.toJson(list, String.class);
        List<Entity> entityList = gson.fromJson(json, new TypeToken<List<Entity>>() {
        }.getType());
        return entityList;
    }

    @Override
    public List<Entity> selectList() throws SQLException {
        List list = baseDao.selectList();
        String json = gson.toJson(list, String.class);
        List<Entity> entityList = gson.fromJson(json, new TypeToken<List<Entity>>() {
        }.getType());
        return entityList;
    }

    @Override
    public int count() throws SQLException {
        return baseDao.count();
    }

    @Override
    public Serializable insert(Entity entity) throws SQLException {
        return null;
    }

    @Override
    public List<Serializable> batchInsert(List<Entity> entities) throws SQLException {
        return null;
    }

    @Override
    public int update(Entity entity) throws SQLException {
        return 0;
    }

    @Override
    public List<Serializable> batchUpdate(List<Entity> entities) throws SQLException {
        return baseDao.batchUpdate(entities);
    }

    @Override
    public int batchDelete(List<Serializable> ids) throws SQLException {
        return baseDao.batchDelete(ids);
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        return baseDao.delete(id);
    }
}
