package pers.hui.common.autoreport.core;

import java.util.List;
import java.util.Map;

/**
 * <code>SqlMapper</code>
 * <desc>
 * 描述：核心功能 是查询数据库
 * 由于年代久远.. 已废弃 可自行实现
 * <desc/>
 * <b>Creation Time:</b> 2021/2/24 0:07.
 *
 * @author Ken.Hu
 */
public class SqlAutoMapper {
    public static List<Map<String, Object>> selectList(String sql, Map<String, Object> paramsMap) {
        return null;
    }

    public static List<Map<String, Object>> selectList(String sql) {
        return null;
    }

    public static String selectOne(String sql, Map<String, Object> paramsMap, Class<String> stringClass) {
        return null;
    }
}
