package com.hui.common.dao;

import com.hui.common.dao.core.BaseDao;
import com.hui.common.dao.core.BaseDaoFactory;
import com.hui.common.dao.core.MysqlDao;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

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
    public void commonDaoTest() throws SQLException {
        // 使用说明:
        // 填充数据库信息
        //    BaseDaoFactory.create()
        // -> DatasourceFactory 数据填充信息,工厂类生成数据源
        // -> SqlServer/Oracle/MysqlDao 策略类构建通用xxDao (通用方式)
        // -> BaseMapper 可以通过xxDao 构建


        // 需求分析:
        // 0. 多表连接查询|复杂查询|单表查询|插入|更新|删除
        // 1. BaseMapper 满足了基础单表映射DAO的CRUD的操作(单表查询|插入|更新|删除)
        // 2. BaseDao 底层CRUD(多表连接查询|复杂查询|复杂更新|复杂删除)

        DataSource dataSource = BaseDaoFactory.dataSourceInstance("com.mysql.cj.jdbc.Driver");
        BaseDao<Map<String,Object>,String> mysqlDao = new MysqlDao<>(dataSource,"t_uc_sys_user");
        Map<String, Object> stringObjectMap = mysqlDao.selectOne("1");
    }

}
