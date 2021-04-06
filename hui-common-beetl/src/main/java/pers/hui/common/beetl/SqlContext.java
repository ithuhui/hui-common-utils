package pers.hui.common.beetl;

import org.beetl.core.Context;
import pers.hui.common.beetl.binding.BindingInfo;

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
    public Map<FunType, SqlParseInfo> getInfo() {
        return (Map<FunType, SqlParseInfo>) this.context.gt.getSharedVars().get(ParseCons.INFO);
    }

    /**
     * 获取转换信息
     *
     * @param funType 标签函数类型
     * @return sql解析信息
     */
    public SqlParseInfo getParseInfo(FunType funType) {
        return getInfo().getOrDefault(funType, new SqlParseInfo());
    }


    /**
     * 获取SQL标签函数绑定信息
     *
     * @param funType 标签函数类型
     * @return 绑定信息 key: 唯一标识 val: 绑定信息
     */
    public Map<String, BindingInfo> getBindingInfoMap(FunType funType) {
        return getParseInfo(funType).getBindingInfoMap();
    }

    /**
     * 获取绑定信息
     *
     * @param funType 标签函数类型
     * @param key     唯一标识
     * @return 绑定信息
     */
    public BindingInfo getBindingInfo(FunType funType, String key) {
        return getParseInfo(funType).getBindingInfoMap().get(key);
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
     * @param funType     方法类型
     * @param key         为一标识
     * @param bindingInfo 绑定信息值
     */
    public void binding(FunType funType, String key, BindingInfo bindingInfo) {
        getBindingInfoMap(funType).put(key, bindingInfo);
    }

    /**
     * 新增标签值
     *
     * @param funType 标签函数类型
     * @param funVal  方法值
     */
    public void addFunVal(FunType funType, FunVal funVal) {
        funVal.setFunType(funType);
        getParseFunValMap(funType).put(funVal.getKey(), funVal);
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
    public boolean needParse(FunType funType) {
        return null != getBindingInfoMap(funType) && getBindingInfoMap(funType).size() != 0;
    }
}
