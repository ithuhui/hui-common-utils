package com.hui.common.dao.core;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

/**
 * <code>CommonQueryRunner</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 0:59.
 *
 * @author Gary.Hu
 */
public class CommonQueryRunner extends QueryRunner {

    public CommonQueryRunner(DataSource dataSource) {
        super(dataSource);
    }

}
