package com.hui.common.dao.core;

import com.hui.common.dao.core.sql.SqlGen;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    protected RunnerDao runnerDao;
    protected String tableName;
    protected String primaryKey;

    public Map<String, String> selectById(Object id) throws SQLException {
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

    public int insert(Map<String, String> dataMap) throws SQLException {
        return insert(dataMap, true);
    }

    public int insert(Map<String, String> entity, boolean ignoreNull) throws SQLException {
        ignoreNullOfMap(entity, ignoreNull);
        String sql = SqlGen.builder()
                .insert(tableName, entity)
                .build()
                .gen();
        return runnerDao.insert(sql);
    }

    public int update(Map<String, String> entity) throws SQLException {
        return update(entity, true);
    }

    public int update(Map<String, String> entity, boolean ignoreNull) throws SQLException {
        String id = entity.get(this.primaryKey);
        if (null == id) {
            throw new SQLException("UPDATE操作必须传入主键");
        }
        entity.remove(this.primaryKey);
        ignoreNullOfMap(entity, ignoreNull);
        String sql = SqlGen.builder()
                .update(tableName, entity)
                .where(primaryKey, "?")
                .build()
                .gen();
        return runnerDao.update(sql, id);
    }


    public int batchDelete(Object... ids) throws SQLException {
        String[] params = new String[ids.length];
        Arrays.fill(params, "?");
        String sql = SqlGen.builder()
                .delete(tableName)
                .where(primaryKey)
                .in(params)
                .build()
                .gen();
        return runnerDao.execute(sql, ids);
    }


    public int delete(Serializable id) throws SQLException {
        String sql = SqlGen.builder()
                .delete(tableName)
                .where(primaryKey, "?")
                .build()
                .gen();
        return runnerDao.execute(sql, id);
    }

    private void ignoreNullOfMap(Map<String, String> dataMap, boolean ignoreNull) {
        if (ignoreNull) {
            dataMap.keySet().stream().forEach(key -> {
                if (null == dataMap.get(key)) {
                    dataMap.remove(key);
                }
            });
        }
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

    public abstract int batchInsert(List<Map<String, String>> maps) throws SQLException;

    public abstract int batchUpdate(List<Map<String, String>> entities) throws SQLException;


}
