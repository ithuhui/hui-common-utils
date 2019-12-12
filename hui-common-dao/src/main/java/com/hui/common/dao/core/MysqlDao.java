package com.hui.common.dao.core;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;

/**
 * <code>MysqlDao</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/11 21:37.
 *
 * @author Gary.Hu
 */
public class MysqlDao<PK extends Serializable> extends BaseDao<Map<String, Object>, PK>{


    public MysqlDao(DataSource dataSource, String tableName) {
        super(dataSource, tableName);
    }
}
