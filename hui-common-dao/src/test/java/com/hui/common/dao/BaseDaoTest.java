package com.hui.common.dao;

import com.hui.common.dao.core.BaseDao;
import com.hui.common.dao.core.BaseDaoFactory;
import com.hui.common.dao.core.RunnerDao;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
    public void baseDaoTest() throws SQLException {
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

        RunnerDao root = BaseDaoFactory.createRunnerDao("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1/hui_cloud_uc?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&&allowPublicKeyRetrieval=true",
                "root",
                "123456");
        BaseDao<Long> baseDao = BaseDaoFactory.createBaseDao(root, "t_uc_sys_user", "user_id");
        int count = baseDao.count();
        System.out.println(count);

        Map<String, String> map = baseDao.selectOne(1);
        System.out.println(map);



        List<Map<String, String>> page = baseDao.selectPage(0, 2);
        page.stream().forEach(x -> System.out.println(x.toString()));

//        Map<String, String> datamap = new HashMap<>();
//        datamap.put("user_id","6");
//        datamap.put("user_name","7654");
//        Long insert = baseDao.insert(datamap);
//        System.out.println(insert);

        List<Map<String, String>> maps = baseDao.selectAll();
        maps.stream().forEach(x -> System.out.println(x.toString()));

        Map<String, String> datamap2 = new HashMap<>();
        datamap2.put("user_id","6");
        datamap2.put("user_name","test");
        int update = baseDao.update(datamap2);
        System.out.println(update);
        maps = baseDao.selectAll();
        maps.stream().forEach(x -> System.out.println(x.toString()));

    }


    @Test
    public void baseMapperTest(){

    }


}
