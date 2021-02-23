package pers.hui.common.dao.core;

import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
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
public class RunnerDao {

    private CommonQueryRunner queryRunner;

    public RunnerDao(DataSource dataSource) {
        this.queryRunner = new CommonQueryRunner(dataSource);
    }

    public BaseDao createBaseDao(String database, String tableName, String primaryKey) throws SQLException {
        database = database.toUpperCase();
        BaseDaoStrategy strategy;
        try {
            strategy = BaseDaoStrategy.valueOf(database);
        } catch (Exception e) {
            throw new SQLException("暂不支持该类型的数据库:" + database);
        }
        return strategy.createBaseDao(this, tableName, primaryKey);
    }

    protected <T> T query(String sql, ResultSetHandler<List<T>> handler, Object... params) throws SQLException {
        List<T> resultList = queryList(sql, handler, params);
        if (resultList.size() != 1) {
            throw new SQLDataException("查询数据结果不唯一");
        }
        return resultList.get(0);
    }

    protected <T> List<T> queryList(String sql, ResultSetHandler<List<T>> handler, Object... params) throws SQLException {
        return queryRunner.query(sql, handler, params);
    }

    protected int update(String sql) throws SQLException {
        return queryRunner.update(sql);
    }

    protected int update(String sql, Object... params) throws SQLException {
        return queryRunner.update(sql, params);
    }

    protected <PK> PK insertReturnKey(String sql, Class<PK> pk, Object... params) throws SQLException {
        return queryRunner.insertReturnKey(sql, pk, params);
    }

    protected int insert(String sql, Object... params) throws SQLException {
        return queryRunner.execute(sql, params);
    }

    protected int batchInsert(String sql, Object... params) throws SQLException {
        return queryRunner.execute(sql, params);
    }

    protected int[] batchExecute(String sql, Object[][] params) throws SQLException {
        return queryRunner.batch(sql, params);
    }

    protected int execute(String sql, Object... params) throws SQLException {
        return queryRunner.execute(sql, params);
    }

    protected <T> List<T> execute(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
        return queryRunner.execute(sql, handler, params);
    }
}
