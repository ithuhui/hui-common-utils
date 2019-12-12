package com.hui.common.dao.core;

import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <code>CommonMapper</code>
 * <desc>
 * 描述： 实现底层接口
 * <desc/>
 * Creation Time: 2019/12/9 0:37.
 *
 * @author Gary.Hu
 */
public abstract class BaseDao<T, PK extends Serializable> implements IBaseMapper<T, PK> {

    public BaseDao(DataSource dataSource,String tableName) {
        this.queryRunner = new CommonQueryRunner(dataSource);
        this.tableName = tableName;
    }

    private CommonQueryRunner queryRunner;
    private String tableName;

    protected T query(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
        return queryRunner.query(sql, handler, params);
    }

    protected List<T> queryList(String sql, ResultSetHandler<List<T>> handler, Object... params) throws SQLException {
        return queryRunner.query(sql, handler, params);
    }

    protected int execute(String sql, Object... params) throws SQLException {
        return queryRunner.execute(sql, params);
    }

    protected int insertWithKey(Map<String, Object> stringObjectMap) {
        return 0;
    }

    @Override
    public T selectOne(Serializable id) throws SQLException {
        String sql = SqlGenerator.select(tableName);
        System.out.println(sql);
        query(sql, new ResultSetHandler<T>() {
            @Override
            public T handle(ResultSet rs) throws SQLException {
                int columnIndex = 0;
                while (rs.next()){
                    String columnName = rs.getMetaData().getColumnName(columnIndex);
                    System.out.println(columnName);
                    columnIndex++;
                }
                return null;
            }
        });
        return null;
    }

    @Override
    public List<T> selectAll() {
        return null;
    }

    @Override
    public List<T> selectPage() {
        return null;
    }

    @Override
    public List<T> selectList() {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public PK insert(T t) {
        return null;
    }

    @Override
    public List<Serializable> batchInsert(List<T> ts) {
        return null;
    }

    @Override
    public int update(T t) {
        return 0;
    }

    @Override
    public List<Serializable> batchUpdate(List<T> ts) {
        return null;
    }

    @Override
    public int batchDelete(List<Serializable> ids) {
        return 0;
    }

    @Override
    public int delete(Serializable id) {
        return 0;
    }
}
