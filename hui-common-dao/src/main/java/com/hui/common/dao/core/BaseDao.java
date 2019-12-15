package com.hui.common.dao.core;

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
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/11 21:37.
 *
 * @author Gary.Hu
 */
@Slf4j
public abstract class BaseDao<PK extends Serializable> implements IBaseDao<Map<String, String>, PK> {

    public BaseDao(RunnerDao runnerDao, String tableName, String primaryKey) {
        this.runnerDao = runnerDao;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
    }

    private RunnerDao<Map<String, String>, PK> runnerDao;
    public String tableName;
    public String primaryKey;

    @Override
    public Map<String, String> selectOne(Serializable id) throws SQLException {
        final String sql = SqlGenerator.selectBuilder().select(tableName).where("id=?").build().generator();
        return runnerDao.query(sql, rs -> ofMap(rs), id);
    }

    @Override
    public List<Map<String, String>> selectAll() throws SQLException {
        final String sql = SqlGenerator.selectBuilder().select(tableName).build().generator();
        return runnerDao.queryList(sql, rs -> ofMap(rs));
    }

    @Override
    public List<Map<String, String>> selectPage(int pageNum, int pageSize) throws SQLException {
        final String sql = SqlGenerator.selectBuilder().select(tableName).limit(pageNum, pageSize).build().generator();
        return runnerDao.queryList(sql, rs -> ofMap(rs));
    }

    @Override
    public List<Map<String, String>> selectList() throws SQLException {
        final String sql = SqlGenerator
                .selectBuilder()
                .select(tableName)
                .where("")
                .build()
                .generator();
        return runnerDao.queryList(sql, rs -> ofMap(rs));
    }

    @Override
    public int count() throws SQLException {
        final String sql = SqlGenerator
                .selectBuilder()
                .selectCount(tableName)
                .build()
                .generator();
        Map<String, String> resultMap = runnerDao.query(sql, rs -> ofMap(rs));
        String count = resultMap.get("count");
        return null == count ? 0 : Integer.valueOf(count);
    }

    @Override
    public PK insert(Map<String, String> dataMap) throws SQLException {

        return null;
    }

    public abstract List<Serializable> batchInsert(List<Map<String, String>> maps) throws SQLException;

    @Override
    public int update(Map<String, String> stringObjectMap) throws SQLException {
        return 0;
    }

    @Override
    public List<Serializable> batchUpdate(List<Map<String, String>> maps) throws SQLException {
        return null;
    }

    @Override
    public int batchDelete(List<Serializable> ids) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        return 0;
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
