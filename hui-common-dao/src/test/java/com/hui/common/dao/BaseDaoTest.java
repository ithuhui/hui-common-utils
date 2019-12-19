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

        Map<String, String> map = baseDao.selectById(1);
        System.out.println(map);

        List<Map<String, String>> page = baseDao.selectPage(0, 2);
        page.stream().forEach(x -> System.out.println(x.toString()));

        List<Map<String, String>> maps = baseDao.selectAll();
        maps.stream().forEach(x -> System.out.println(x.toString()));

    }


    @Test
    public void otherTest() throws SQLException {
        Map<String, String> dataMap = new HashMap<>();
        //dataMap.put("user_id", "6");
        dataMap.put("user_name", "test");

        //insert
        baseDao.insert(dataMap);

        //update
        dataMap.put("user_id", "8");
        dataMap.put("user_name", "updateTest");
        baseDao.update(dataMap);

        //delete
//        int delete = baseDao.batchDelete(pk);
//        System.out.println("delete result :" + delete);

    }

    @Test
    public void batchTest() throws SQLException {
        //batch
        Map<String, String> data1 = new HashMap<>();
        data1.put("user_name", "test1");

        Map<String, String> data2 = new HashMap<>();
        data2.put("user_name", "test2");

        List<Map<String, String>> maps = Arrays.asList(data1, data2);
//        int affectRow1 = baseDao.batchInsert(maps);
//        System.out.println(affectRow1);


        data1.put("user_id", "12");
        data1.put("user_name", "update1");

        data2.put("user_id", "13");
        data2.put("user_name", "update2");
        maps = Arrays.asList(data1, data2);
        int affectRow2 = baseDao.batchUpdate(maps);
        System.out.println(affectRow2);
    }
    @Test
    public void baseMapperTest() {
        String[] params = new String[6];
        Arrays.fill(params, "?");
        System.out.println(Arrays.toString(params));
    }


}
