package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;

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

      return null;
    }

}
