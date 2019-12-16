package com.hui.common.dao.core;

import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

/**
 * <code>CommonMapper</code>
 * <desc>
 * 描述： 实现底层接口
 * <desc/>
 * Creation Time: 2019/12/9 0:37.
 *
 * @author Gary.Hu
 */
public class RunnerDao<T, PK extends Serializable> {

    public RunnerDao(DataSource dataSource) {
        this.queryRunner = new CommonQueryRunner(dataSource);
    }

    private CommonQueryRunner<PK> queryRunner;

    protected T query(String sql, ResultSetHandler<List<T>> handler, Object... params) throws SQLException {
        List<T> resultList = queryList(sql, handler, params);
        if (resultList.size() != 1) {
            throw new SQLDataException("查询数据结果不唯一");
        }
        return resultList.get(0);
    }

    protected List<T> queryList(String sql, ResultSetHandler<List<T>> handler, Object... params) throws SQLException {
        return queryRunner.query(sql, handler, params);
    }

    protected int update(String sql,Object... params) throws SQLException {
        return queryRunner.update(sql, params);
    }

    protected PK insertReturnKey(String sql,Object... params) throws SQLException {
        return queryRunner.insertReturnKey(sql, params);
    }

    protected List<PK> batchInsertReturnKeys(){
        return null;
    }

    protected int batchExecute(String sql, Object[][] params) throws SQLException {
        queryRunner.batch(sql, params);
        return 0;
    }

    protected int execute(String sql, Object... params) throws SQLException {
        return queryRunner.execute(sql, params);
    }

    protected List<T> execute(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
        return queryRunner.execute(sql, handler, params);
    }
}
