package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.BindingInfo;
import pers.hui.common.beetl.binding.IncludeBinding;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.Map;

/**
 * <code>IncludeFun</code>
 * <desc>
 * 描述：include标签函数
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 14:21.
 *
 * @author Gary.Hu
 */
public class IncludeFun implements Function {
    public static final String TEMPLATE_BASE = "base";
    public static final String TEMPLATE_GLOBAL_VAL = "global_val";

    /**
     * #{include("base","t_user")}
     *
     * @param params  参数
     * @param context 模板上下文
     * @return include进来的模板，需要解析完成的
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object call(Object[] params, Context context) {
        SqlContext sqlContext = SqlContext.instance(context);
        String type = String.valueOf(params[0]);
        String contentCode = String.valueOf(params[1]);

        if (type.equalsIgnoreCase(TEMPLATE_BASE)) {
            Map<String, BindingInfo> bindingInfoMap = sqlContext.getBindingInfoMap(FunType.INCLUDE_BASE);
            IncludeBinding bindingInfo = (IncludeBinding) bindingInfoMap.get(ParseUtils.keyGen(contentCode));
            if (!sqlContext.needParse(FunType.INCLUDE_BASE)){
                return ParseCons.EMPTY_STR;
            }
            return parse(contentCode, bindingInfo.getIncludeContent());
        }

        if (type.equalsIgnoreCase(TEMPLATE_GLOBAL_VAL)) {
            Map<String, BindingInfo> bindingInfoMap = sqlContext.getBindingInfoMap(FunType.INCLUDE_GLOBAL_VAL);
            IncludeBinding bindingInfo = (IncludeBinding) bindingInfoMap.get(ParseUtils.keyGen(contentCode));
            if (!sqlContext.needParse(FunType.INCLUDE_GLOBAL_VAL)){
                return ParseCons.EMPTY_STR;
            }
            return bindingInfo.getIncludeContent();
        }

        return ParseCons.EMPTY_STR;
    }

    private String parse(String code, String includeContent) {
        return String.format("%s as ( %s )", code, includeContent);
    }

    private String parseWithFun(){
        return null;
    }
}
