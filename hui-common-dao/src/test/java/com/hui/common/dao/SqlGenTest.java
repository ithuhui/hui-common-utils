package com.hui.common.dao;

import com.hui.common.dao.core.sql.SqlGen;
import javafx.util.Pair;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <code>SqlGenTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/18 23:36.
 *
 * @author Gary.Hu
 */
public class SqlGenTest {
    @Test
    public void testSQL() {
        //SELECT
        // selectALL: select * from table;
        SqlGen.builder().select().from("table").build().gen();
        // selectOne: select * from table where id = 1;
        SqlGen.builder().select().from("table").where("id", "1").build().gen();
        // selectList: select * from table where id > 10 and name like '%gary%';
        SqlGen.builder().select().from("table").where("id", ">", "10").where("name").like("gary").build().gen();
        // selectPage: select * from table where id > 10 limit 10,20;
        SqlGen.builder().select().from("table").where("id", ">", "10").limit(10, 20).build().gen();
        // count: select count(1) from table;
        SqlGen.builder().selectCount().from("table").build().gen();


        // INSERT/UPDATE/DELETE
        Map<String, String> map = new LinkedHashMap<>();
        map.put("field1", "val1");
        map.put("field2", "val2");
        map.put("field3", "val3");
        // insert: insert into table (field1,field2,field3) values ('val1','val2','val3');
        SqlGen.builder().insert("table", map).build().gen();
        // update: update table set field1='val1',field2='val2', field3='val3' where id = 1
        SqlGen.builder().update("table", map).where("id = 1").build().gen();

        // delete: delete from table where id = 1
        SqlGen.builder().delete("table").where("id = 1").build().gen();

        //BATCH
        // batchInsert: insert into table (field1,field2,field3) values ('va1','val2','val3'),('val4','val5','val6')
        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("field1", "val4");
        map2.put("field2", "val5");
        map2.put("field3", "val6");
        List<Map<String, String>> dataList = Arrays.asList(map, map2);
        SqlGen.builder().insertBatch("table", dataList).build().gen();

        // batchUpdate: insert into mytable(id, myfield1, myfield2) values (1,'value11','value21'),(2,'value12','value22'),(3,'value13','value23') on duplicate key update myfield1=values(myfield2),values(myfield2)+values(id);
        SqlGen.builder().updateBatch("table", dataList).build().gen();
        // batchdelete: delete from table where id in(1,2,3)
        SqlGen.builder().delete("table").in("1", "2", "3").build().gen();
    }

    @Test
    public void test() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("field1", "val1");
        map.put("field2", "val2");
        map.put("field3", "val3");

        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("field1", "val4");
        map2.put("field2", "val5");
        map2.put("field3", null);

        List<Map<String, String>> dataList = Arrays.asList(map, map2);
        List<String> keyList = new ArrayList<>(dataList.get(0).keySet());
        String keys = String.join(",", keyList);
        List<String> valuesList = dataList.stream()
                .map(
                        data -> keyList.stream().map(key -> data.get(key))
                                .collect(Collectors.joining(","))
                ).collect(Collectors.toList());
        Pair<String, List<String>> result = new Pair<>(keys, valuesList);
        System.out.println(result);
    }
}
