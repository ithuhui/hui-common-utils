package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.SqlContext;

/**
 * <code>HavingFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/7 18:21.
 *
 * @author Gary.Hu
 */
public class HavingFun implements Function {
    /**
     * #{having("group")}
     * @param params 模板入参
     * @param context 模板上下文
     * @return
     */
    @Override
    public Object call(Object[] params, Context context) {
        SqlContext sqlContext = SqlContext.instance(context);
        String group = String.valueOf(params[0]);

        return null;
    }

}
