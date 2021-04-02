package pers.hui.common.beetl;

import org.beetl.core.Context;
import pers.hui.common.beetl.binding.BindingInfo;

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

    @SuppressWarnings("unchecked")
    private Map<FunType, ParseInfo> getInfo() {
        return (Map<FunType, ParseInfo>) this.context.gt.getSharedVars().get(ParseCons.INFO);

    }

    public ParseInfo getParseInfo(FunType funType) {
        return getInfo().get(funType);
    }


    public List<FunVal> getFunVals(FunType funType) {
        return null;
    }

    public Map<String, BindingInfo> getBindingInfoMap(FunType funType) {
        return getParseInfo(funType).getBindingInfoMap();
    }

    public Map<String, FunVal> getParseFunValMap(FunType funType) {
        return getParseInfo(funType).getParseFunValMap();
    }

    public void binding(FunType funType, String key, BindingInfo bindingInfo) {
        getBindingInfoMap(funType).put(key, bindingInfo);
    }

    public void addFunVal(FunType funType, FunVal funVal) {
        funVal.setFunType(funType);
        getParseFunValMap(funType).put(funVal.getKey(), funVal);
    }

    public void setParseVal(FunType funType, String key, String parseResult) {
        getParseFunValMap(funType).get(key).setParseVal(parseResult);
    }

    public boolean needParse(FunType funType) {
        return null != getBindingInfoMap(funType);
    }
}
