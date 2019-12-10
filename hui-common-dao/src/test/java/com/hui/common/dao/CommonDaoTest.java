package com.hui.common.dao;

import org.junit.Test;

/**
 * <code>CommonDaoTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/10 4:20.
 *
 * @author Gary.Hu
 */
public class CommonDaoTest {

    @Test
    public void commonDaoTest(){
        // 填充数据库信息
        //    BaseDaoFactory.create()
        // -> DatasourceFactory 数据填充信息,工厂类生成数据源
        // -> SqlServer/Oracle/MysqlDao 策略类构建通用xxDao (通用方式)
        // -> BaseMapper 可以通过xxDao 构建
    }
}
