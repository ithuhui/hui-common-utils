package com.hui.common.dao.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <code>CommonSqlRunner</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 21:28.
 *
 * @author Gary.Hu
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlGenerator {

    private String tableName;
    private String whereCondition;
    private String limitStr;
    private String fieldStr;
    private boolean ignoreNullField;
    private StringBuilder finalSql;

    public static SelectBuilder selectBuilder() {
        return new SelectBuilder();
    }

    public static UpdateBuilder updateBuilder() {
        return new UpdateBuilder();
    }

    public static InsertBuilder insertBuilder() {
        return new InsertBuilder();
    }

    public static DeleteBuilder deleteBuilder() {
        return new DeleteBuilder();
    }

    public String generator() {
        this.finalSql = this.finalSql.append(";");
        System.out.println(finalSql);
        return finalSql.toString();

    }

    public static class WhereBuilder {

    }

    public static class InsertBuilder extends WhereBuilder {
        private String tableName;
        private String whereCondition;
        private String limitStr;
        private String fieldStr;
        private boolean ignoreNullField;
        private StringBuilder finalSql = new StringBuilder();

        public InsertBuilder insert(String tableName) {
            this.tableName = tableName;
            String sql = String.format("insert into %s ", tableName);
            this.finalSql.append(sql);
            return this;
        }

        public InsertBuilder fields(String... fields) {
            this.fieldStr = Arrays.stream(fields).collect(Collectors.joining(","));
            String sql = String.format("(%s) ", this.fieldStr);
            this.finalSql.append(sql);
            return this;
        }

        public InsertBuilder values(String... values) {
            String sql = Arrays.stream(values)
                    .map(value -> "(" + value + ")")
                    .collect(Collectors.joining(","));
            sql = "values ".concat(sql);
            this.finalSql.append(sql);
            return this;
        }

        public SqlGenerator build() {
            return new SqlGenerator(tableName, whereCondition, limitStr, fieldStr, ignoreNullField, finalSql);
        }
    }

    public static class DeleteBuilder {
        private String tableName;
        private String whereCondition;
        private String limitStr;
        private String fieldStr;
        private boolean ignoreNullField;
        private StringBuilder finalSql = new StringBuilder();

        public DeleteBuilder delete(String tableName) {
            this.tableName = tableName;
            String sql = String.format("delete from %s ", tableName);
            finalSql.append(sql);
            return this;
        }

        public DeleteBuilder in(String... ids) {
            String sql = String.format("in (%s)", String.join(",", ids));
            this.finalSql.append(sql);
            return this;
        }

        public DeleteBuilder wherePK(String primaryKey) {
            return where(primaryKey + "=?");
        }

        public DeleteBuilder where(String expression) {
            if (!expression.trim().startsWith("where")) {
                expression = "where ".concat(expression);
            }
            this.whereCondition = expression;
            finalSql.append(this.whereCondition);
            return this;
        }

        public SqlGenerator build() {
            return new SqlGenerator(tableName, whereCondition, limitStr, fieldStr, ignoreNullField, finalSql);
        }
    }

    public static class UpdateBuilder {
        private String tableName;
        private String whereCondition;
        private String limitStr;
        private String fieldStr;
        private boolean ignoreNullField;
        private StringBuilder finalSql = new StringBuilder();

        public UpdateBuilder update(String tableName) {
            this.tableName = tableName;
            String sql = String.format("update %s", tableName);
            this.finalSql.append(sql);
            return this;
        }

        public UpdateBuilder setFields(Map<String,String> maps,String primaryKey) {

            maps.remove(primaryKey);
            Set<Map.Entry<String, String>> entries = maps.entrySet();
            String sql = entries.stream().map(entry -> entry.getKey() + "='" + entry.getValue() + "'").collect(Collectors.joining(","));
            this.finalSql.append(String.format(" set %s ", sql));
            return this;
        }

        public UpdateBuilder wherePK(String primaryKey) {
            return where(primaryKey + "=?");
        }

        public UpdateBuilder where(String expression) {
            if (!expression.trim().startsWith("where")) {
                expression = "where ".concat(expression);
            }
            this.whereCondition = expression;
            finalSql.append(this.whereCondition);
            return this;
        }

        public SqlGenerator build() {
            return new SqlGenerator(tableName, whereCondition, limitStr, fieldStr, ignoreNullField, finalSql);
        }
    }

    public static class SelectBuilder {
        private String tableName;
        private String whereCondition;
        private String limitStr;
        private String fieldStr;
        private boolean ignoreNullField;
        private StringBuilder finalSql = new StringBuilder();

        public SelectBuilder select(String tableName) {
            this.fieldStr = "*";
            return select(tableName, new String[]{this.fieldStr});
        }

        public SelectBuilder selectCount(String tableName) {
            this.fieldStr = "count(1) as count";
            return select(tableName, new String[]{this.fieldStr});
        }

        public SelectBuilder select(String tableName, String... fields) {
            this.tableName = tableName;
            this.fieldStr = Arrays.stream(fields).collect(Collectors.joining(","));
            String sql = String.format("select %s from %s ", fieldStr, tableName);
            finalSql.append(sql);
            return this;
        }


        public SelectBuilder limit(int size) {
            this.limitStr = String.format(" limit %s", size);
            finalSql.append(this.limitStr);
            return this;
        }

        public SelectBuilder limit(int offset, int size) {
            this.limitStr = String.format(" limit %s,%s", String.valueOf(offset), String.valueOf(size));
            finalSql.append(limitStr);
            return this;
        }

        public SelectBuilder wherePK(String primaryKey) {
            return where(primaryKey + "=?");
        }

        public SelectBuilder where(String expression) {
            if (!expression.trim().startsWith("where")) {
                expression = "where ".concat(expression);
            }
            this.whereCondition = expression;
            finalSql.append(this.whereCondition);
            return this;
        }

        public SqlGenerator build() {
            return new SqlGenerator(tableName, whereCondition, limitStr, fieldStr, ignoreNullField, finalSql);
        }
    }


    public static void main(String[] args) {
        //SELECT
        // selectALL: select * from table;
        SqlGenerator.selectBuilder().select("t_uc_sys_user").build().generator();
        // selectOne: select * from table where id = 1;
        SqlGenerator.selectBuilder().select("t_uc_sys_user").where("id=1").build().generator();
        // selectList: select * from table where id > 10 and name like '%gary%';
        SqlGenerator.selectBuilder().select("t_uc_sys_user").where("id=>10 and name like '%gary%'").build().generator();
        // selectPage: select * from table where id > 10 limit 10,20;
        SqlGenerator.selectBuilder().select("t_uc_sys_user").where("id=>10").limit(10, 20).build().generator();
        // count: select count(1) from table;
        SqlGenerator.selectBuilder().selectCount("t_uc_sys_user").build().generator();


        // INSERT/UPDATE/DELETE
        // entity: user :{id='1',name='gary.hu',create_time='2019-12-14 10:00:00'}
        // insert: insert into table (field1,field2,field3) values ('val1','val2','val3');
        SqlGenerator.insertBuilder().insert("t_uc_sys_user").fields("field1", "field2", "field3").values("val1,val2,val3").build().generator();
        // update: update table set field1='val1',field2='val2', field3='val3' where id = 1
        Map<String, String> map = new HashMap<>();
        map.put("field1", "val1");
        map.put("field2", "val2");
        map.put("field3", "val3");
        SqlGenerator.updateBuilder().update("t_uc_sys_user").setFields(map,"id").wherePK("id").build().generator();
        // delete: delete from table where id = 1
        SqlGenerator.deleteBuilder().delete("t_uc_sys_user").where("id=1").build().generator();


        //BATCH
        // batchInsert: insert into table (field1,field2,field3) values ('va1','val2','val3'),('val4','val5','val6')
        SqlGenerator.insertBuilder().insert("t_uc_sys_user").fields("field1", "field2", "field3").values("val1,val2,val3", "val4,val5,val6").build().generator();
        // batchUpdate: update
        // batchdelete: delete from table where id in(1,2,3)
        SqlGenerator.deleteBuilder().delete("t_uc_sys_user").in("1", "2", "3").build().generator();
    }
}
