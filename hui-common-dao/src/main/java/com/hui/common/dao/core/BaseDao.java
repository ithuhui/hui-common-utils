package com.hui.common.dao.core;

import com.hui.common.dao.core.sql.SqlGen;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <code>MysqlDao</code>
 * <desc>
 * 描述： 针对单表的底层封装
 * <desc/>
 * Creation Time: 2019/12/11 21:37.
 *
 * @author Gary.Hu
 */
@Slf4j
public abstract class BaseDao<PK extends Serializable> {

    BaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
        this.runnerDao = runnerDao;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
    }

    private RunnerDao runnerDao;
    private String tableName;
    private String primaryKey;

    public void test() {
    }

    public Map<String, String> selectOne(Serializable id) throws SQLException {
        final String sql = SqlGen.builder()
                .select()
                .from(tableName)
                .where(primaryKey, "?")
                .build()
                .gen();
        return runnerDao.query(sql, rs -> ofMap(rs), id);
    }


    public List<Map<String, String>> selectAll() throws SQLException {
        final String sql = SqlGen.builder()
                .select()
                .from(tableName)
                .build()
                .gen();
        return runnerDao.queryList(sql, rs -> ofMap(rs));
    }


    public List<Map<String, String>> selectPage(int pageNum, int pageSize) throws SQLException {
        final String sql = SqlGen.builder()
                .select()
                .from(tableName)
                .limit(pageNum, pageSize)
                .build()
                .gen();
        return runnerDao.queryList(sql, rs -> ofMap(rs));
    }


    public List<Map<String, String>> selectList() throws SQLException {
        final String sql = SqlGen
                .builder()
                .select(tableName)
                .where("")
                .build()
                .gen();
        return runnerDao.queryList(sql, rs -> ofMap(rs));
    }


    public int count() throws SQLException {
        final String sql = SqlGen
                .builder()
                .selectCount()
                .build()
                .gen();
        Map<String, String> resultMap = runnerDao.query(sql, rs -> ofMap(rs));
        String count = resultMap.get("count");
        return null == count ? 0 : Integer.valueOf(count);
    }


    public PK insert(Map<String, String> dataMap) throws SQLException {
        String sql = SqlGen.builder()
                .insert(tableName, dataMap)
                .build()
                .gen();
        return (PK) runnerDao.insertReturnKey(sql, Long.class);
    }

    public abstract List<Serializable> batchInsert(List<Map<String, String>> maps) throws SQLException;


    public int update(Map<String, String> entity) throws SQLException {
        String id = entity.get(primaryKey);
        entity.remove(primaryKey);
        String sql = SqlGen.builder()
                .update(tableName, entity)
                .where(primaryKey, "?")
                .build()
                .gen();
        return runnerDao.update(sql, id);
    }

    public abstract int batchUpdate(List<Map<String, String>> entities) throws SQLException;


    public int batchDelete(List<Serializable> ids) throws SQLException {
        String sql = SqlGen.builder()
                .delete(tableName)
                .where(primaryKey)
                .in(ids.toArray(new String[ids.size()]))
                .build()
                .gen();
        return runnerDao.execute(sql);
    }


    public int delete(Serializable id) throws SQLException {
        String sql = SqlGen.builder()
                .delete(tableName)
                .where(primaryKey, "?")
                .build()
                .gen();
        return runnerDao.execute(sql, id);
    }

    private List<Map<String, String>> ofMap(ResultSet rs) throws SQLException {
        List<Map<String, String>> dataList = new LinkedList<>();
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            Map<String, String> data = new LinkedHashMap<>();
            int index = 0;
            while (index++ < columnCount) {
                String columnLabel = rs.getMetaData().getColumnName(index);
                String val = rs.getString(columnLabel);
                data.put(columnLabel, val);
            }
            dataList.add(data);
        }
        return dataList;
    }
}
