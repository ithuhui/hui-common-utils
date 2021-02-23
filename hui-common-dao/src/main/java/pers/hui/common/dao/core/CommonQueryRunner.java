package pers.hui.common.dao.core;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.*;

/**
 * <code>CommonQueryRunner</code>
 * <desc>
 * 描述： 对接apache queryRunner 做底层接口封装
 * <desc/>
 * Creation Time: 2019/12/9 0:59.
 *
 * @author Gary.Hu
 */
public class CommonQueryRunner extends QueryRunner {

    public CommonQueryRunner(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 自增的PK才有效果,暂时不用于BaseDao的insert操作,保留方法
     * @param sql
     * @param pk
     * @param params
     * @param <PK>
     * @return
     * @throws SQLException
     */
    public <PK> PK insertReturnKey(String sql, Class<PK> pk, Object... params) throws SQLException {
        Connection connection = this.prepareConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.fillStatement(stmt, params);
            stmt.executeUpdate();
            ResultSet rsKey = stmt.getGeneratedKeys();
            PK key = null;
            if (rsKey.next()) {
                key = rsKey.getObject(1, pk);
            }
            return key;
        } catch (SQLException e) {
            this.rethrow(e, sql, params);

        } finally {
            close(stmt);
        }
        return null;
    }

}
