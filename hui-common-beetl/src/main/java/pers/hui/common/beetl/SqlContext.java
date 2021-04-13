package pers.hui.common.beetl;

import org.beetl.core.Context;
import pers.hui.common.beetl.binding.Binding;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <code>SqlContext</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 21:53.
 *
 * @author Ken.Hu
 */
public class SqlContext {
    private final Context context;
    private static SqlContext instance;

    private SqlContext(Context context) {
        this.context = context;
    }


    public static synchronized SqlContext instance(Context context) {
        if (instance == null) {
            instance = new SqlContext(context);
        }
        return instance;
    }

    /**
     * 从共享变量获取SQL解析信息
     *
     * @return SQL解析信息
     */
    @SuppressWarnings("unchecked")
    public Map<FunType, SqlParseInfo<Binding>> getInfo() {
        Map<FunType, SqlParseInfo<Binding>> sqlParseInfoMap = (Map<FunType, SqlParseInfo<Binding>>) this.context.gt.getSharedVars().putIfAbsent(ParseCons.INFO, new HashMap<FunType, SqlParseInfo<Binding>>());
        return null == sqlParseInfoMap ? (Map<FunType, SqlParseInfo<Binding>>) this.context.gt.getSharedVars().get(ParseCons.INFO) : sqlParseInfoMap;
    }

    /**
     * 获取转换信息
     *
     * @param funType 标签函数类型
     * @return sql解析信息
     */
    public SqlParseInfo<Binding> getParseInfo(FunType funType) {
        SqlParseInfo<Binding> sqlParseInfo = getInfo().putIfAbsent(funType, new SqlParseInfo<>());
        return null == sqlParseInfo ? getInfo().get(funType) : sqlParseInfo;
    }


    /**
     * 获取SQL标签函数绑定信息
     *
     * @param funType 标签函数类型
     * @return 绑定信息 key: 唯一标识 val: 绑定信息
     */
    public <T extends Binding> Map<String, List<T>> getBindingMap(FunType funType,Class<T> type) {
        SqlParseInfo<Binding> parseInfo = getParseInfo(funType);
        Map<String, List<Binding>> bindingMap = parseInfo.getBindingMap();
        HashMap<String, List<T>> hashMap = new HashMap<>(bindingMap.size());
        bindingMap.forEach((k,v)->{
            List<T> collect = v.stream().map(type::cast).collect(Collectors.toList());
            hashMap.putIfAbsent(k,collect);
        });
        return hashMap;
    }

    public Map<String, List<Binding>> getBindingMap(FunType funType) {
        return getParseInfo(funType).getBindingMap();
    }

    /**
     * 获取绑定信息
     *
     * @param funType 标签函数类型
     * @param key     唯一标识
     * @return 绑定信息
     */
    public <T> List<T> getBindingInfo(FunType funType, String key , Class<T> type) {
        List<Binding> bindings = getParseInfo(funType).getBindingMap().get(key);
        return bindings.stream().map(type::cast).collect(Collectors.toList());
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
    public void binding(FunType funType, String key, Binding binding) {
        List bindingList = getBindingMap(funType).putIfAbsent(key, Collections.singletonList(binding));
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
     * 判断是否需要转换
     *
     * @param funType 标签函数类型
     * @return 是否需要转换
     */
    public <T> boolean notNeededParse(FunType funType) {
        return null == getBindingMap(funType) || getBindingMap(funType).size() == 0;
    }
}
