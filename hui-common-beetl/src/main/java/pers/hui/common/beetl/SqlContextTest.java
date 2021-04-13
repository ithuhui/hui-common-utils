package pers.hui.common.beetl;

import org.beetl.core.Context;
import pers.hui.common.beetl.binding.Binding;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>SqlContext</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 21:53.
 *
 * @author Ken.Hu
 */
public class SqlContextTest<T extends Binding> {
    private final Context context;
    private static SqlContextTest instance;

    private SqlContextTest(Context context) {
        this.context = context;
    }


    public static synchronized SqlContextTest instance(Context context) {
        if (instance == null) {
            instance = new SqlContextTest(context);
        }
        return instance;
    }

    /**
     * 从共享变量获取SQL解析信息
     *
     * @return SQL解析信息
     */
    @SuppressWarnings("unchecked")
    public Map<FunType, SqlParseInfo<T>> getInfo() {
        Map<FunType, SqlParseInfo<T>> sqlParseInfoMap = (Map<FunType, SqlParseInfo<T>>) this.context.gt.getSharedVars().putIfAbsent(ParseCons.INFO, new HashMap<FunType, SqlParseInfo<T>>());
        return null == sqlParseInfoMap ? (Map<FunType, SqlParseInfo<T>>) this.context.gt.getSharedVars().get(ParseCons.INFO) : sqlParseInfoMap;
    }

    /**
     * 获取转换信息
     *
     * @param funType 标签函数类型
     * @return sql解析信息
     */
    public SqlParseInfo<T> getParseInfo(FunType funType) {
        SqlParseInfo<T> sqlParseInfo = getInfo().putIfAbsent(funType, new SqlParseInfo<T>());
        return null == sqlParseInfo ? getInfo().get(funType) : sqlParseInfo;
    }


    /**
     * 获取SQL标签函数绑定信息
     *
     * @param funType 标签函数类型
     * @return 绑定信息 key: 唯一标识 val: 绑定信息
     */
    public Map<String, List<T>> getBindingMap(FunType funType) {
        return getParseInfo(funType).getBindingMap();
    }
    

    /**
     * 获取绑定信息
     *
     * @param funType 标签函数类型
     * @param key     唯一标识
     * @return 绑定信息
     */
    public List<T> getBindingInfo(FunType funType, String key) {
        return getParseInfo(funType).getBindingMap().get(key);
    }

    /**
     * 获取标签函数信息
     *
     * @param funType 标签函数类型
     * @return 标签函数信息 key: 唯一标识 val: 方法信息
     */
    public Map<String, FunVal> getParseFunValMap(FunType funType) {
        return getParseInfo(funType).getParseFunValMap();
    }

    /**
     * 获取标签函数元信息
     *
     * @param funType 标签函数类型
     * @return 函数值
     */
    public FunVal getParseFunVal(FunType funType, String key) {
        return getParseInfo(funType).getParseFunValMap().get(key);
    }


    /**
     * 绑定信息
     *
     * @param funType 方法类型
     * @param key     为一标识
     * @param binding 绑定信息值
     */
    public void binding(FunType funType, String key, T binding) {
        List<T> bindingList = getBindingMap(funType).putIfAbsent(key, Collections.singletonList(binding));
        if (null == bindingList) {
            getBindingMap(funType).get(key).add(binding);
        }
    }

    /**
     * 新增标签值
     *
     * @param funType 标签函数类型
     * @param funVal  方法值
     */
    public void addFunVal(FunType funType, FunVal funVal) {
        funVal.setFunType(funType);
        getParseFunValMap(funType)
                .put(funVal.getKey(), funVal);
    }

    /**
     * 绑定解析完后的值
     *
     * @param funType     标签函数类型
     * @param key         唯一标识
     * @param parseResult 转换结果
     */
    public void setParseVal(FunType funType, String key, String parseResult) {
        getParseFunValMap(funType).get(key).setParseVal(parseResult);
    }

    /**
     * 判断是否需要转换
     *
     * @param funType 标签函数类型
     * @return 是否需要转换
     */
    public boolean notNeededParse(FunType funType) {
        return null == getBindingMap(funType) || getBindingMap(funType).size() == 0;
    }
}
