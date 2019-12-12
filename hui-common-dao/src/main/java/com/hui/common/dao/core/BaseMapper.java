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
    public List<Entity> selectAll() {
        List list = baseDao.selectAll();
        String json = gson.toJson(list, String.class);
        List<Entity> entityList = gson.fromJson(json, new TypeToken<List<Entity>>() {
        }.getType());
        return entityList;
    }

    @Override
    public List<Entity> selectPage() {
        List list = baseDao.selectAll();
        String json = gson.toJson(list, String.class);
        List<Entity> entityList = gson.fromJson(json, new TypeToken<List<Entity>>() {
        }.getType());
        return entityList;
    }

    @Override
    public List<Entity> selectList() {
        List list = baseDao.selectList();
        String json = gson.toJson(list, String.class);
        List<Entity> entityList = gson.fromJson(json, new TypeToken<List<Entity>>() {
        }.getType());
        return entityList;
    }

    @Override
    public int count() {
        return baseDao.count();
    }

    @Override
    public Serializable insert(Entity entity) {
        return baseDao.insert(entity);
    }

    @Override
    public List<Serializable> batchInsert(List<Entity> entities) {
        return baseDao.batchInsert(entities);
    }

    @Override
    public int update(Entity entity) {
        return baseDao.update(entity);
    }

    @Override
    public List<Serializable> batchUpdate(List<Entity> entities) {
        return baseDao.batchUpdate(entities);
    }

    @Override
    public int batchDelete(List<Serializable> ids) {
        return baseDao.batchDelete(ids);
    }

    @Override
    public int delete(Serializable id) {
        return baseDao.delete(id);
    }
}
