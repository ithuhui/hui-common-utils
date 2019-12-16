package com.hui.common.dao;

import com.hui.common.dao.core.*;
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
public class BaseDaoTest {

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

        DataSource dataSource =
                BaseDaoFactory.dataSourceInstance("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1/hui_cloud_uc?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&&allowPublicKeyRetrieval=true",
                "root",
                "123456");
        RunnerDao runnerDao = new RunnerDao(dataSource);
        BaseDao<String> mysqlDao = new MySqlDao<>(runnerDao, "t_uc_sys_user", "user_id");
        Map<String, String> resultMap = mysqlDao.selectOne("1");
        System.out.println(resultMap.toString());

        int count = mysqlDao.count();
        System.out.println(count);

        RunnerDao root = BaseDaoFactory.createRunnerDao("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1/hui_cloud_uc?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&&allowPublicKeyRetrieval=true",
                "root",
                "123456");
        BaseDao baseDao = BaseDaoFactory.createBaseDao(root, "t_uc_sys_user", "user_id");
        Map map = baseDao.selectOne(1);
        System.out.println(map);

    }

    @Test
    public void sqlGenTest(){
        String sql = SqlGenerator.selectBuilder().select("t_uc_sys_user").where("id=?").build().generator();
    }

}
