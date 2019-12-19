package com.hui.common.dao;

import com.hui.common.dao.core.BaseDao;
import com.hui.common.dao.core.RunnerDaoFactory;
import com.hui.common.dao.core.RunnerDao;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
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

    private RunnerDao runnerDao;
    private BaseDao<Long> baseDao;

    @Before
    public void before() throws SQLException {
        runnerDao = RunnerDaoFactory.createRunnerDao("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1/hui_cloud_uc?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&&allowPublicKeyRetrieval=true",
                "root",
                "123456");

        baseDao = runnerDao.createBaseDao("mysql", "t_uc_sys_user", "user_id");
    }

    @Test
    public void selectTest() throws SQLException {
        // select
        int count = baseDao.count();
        System.out.println(count);

        Map<String, String> map = baseDao.selectOne(1);
        System.out.println(map);

        List<Map<String, String>> page = baseDao.selectPage(0, 2);
        page.stream().forEach(x -> System.out.println(x.toString()));

        List<Map<String, String>> maps = baseDao.selectAll();
        maps.stream().forEach(x -> System.out.println(x.toString()));

        //TODO
        baseDao.selectList();
    }


    @Test
    public void otherTest() throws SQLException {
        //delete
        int delete = baseDao.batchDelete(Arrays.asList("6","7"));
        System.out.println("delete result :" + delete);

        //insert
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("user_id", "6");
        dataMap.put("user_name", "test");
        Long pk = baseDao.insert(dataMap);
        System.out.println(pk);

        //update
        dataMap.put("user_id", "6");
        dataMap.put("user_name", "updateTest");
        baseDao.update(dataMap);


    }

    @Test
    public void baseMapperTest() {

    }


}
