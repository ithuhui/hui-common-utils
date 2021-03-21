package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.model.IncludeBinding;
import pers.hui.common.beetl.model.SqlKey;
import pers.hui.common.beetl.model.info.Include;

import java.util.Map;

/**
 * <code>IncludeFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 14:21.
 *
 * @author Gary.Hu
 */
public class IncludeFun implements Function {

    private static final String INCLUDE_SYMBOL = "INCLUDE_";

    /**
     * #{include("base","t_user")}
     *
     * @param params  参数
     * @param context 模板上下文
     * @return include进来的模板，需要解析完成的
     */
    @Override
    public Object call(Object[] params, Context context) {
        String type = (String) params[0];
        String contentCode = (String) params[1];
        Include include = (Include) context.getGlobal(SqlKey.INCLUDE.name());
        Map<String, IncludeBinding> includeBindingMap;
        if (type.equals("base")) {
            includeBindingMap = include.getBaseTemplate();
        } else if (type.equals("global_val")) {
            includeBindingMap = include.getGlobalTemplate();
        } else {
            return "";
        }
        IncludeBinding includeBinding = includeBindingMap.get(contentCode);
        return includeBinding.getIncludeContent();
    }
}
