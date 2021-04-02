package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * <code>WhereFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/16 23:06.
 *
 * @author Ken.Hu
 */
public class WhereFun implements Function {

    /**
     * way1: 全部字段都显示声明 ： #{whereField("code", "comment", "val", "group")}
     * way2: 从context获取用户定义的where字段 #{where("group")}
     * way3: 语法树解析
     *
     * @param params  入参
     * @param context 模板上下文
     * @return 解析成功的字符串
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object call(Object[] params, Context context) {
        return null;
    }
}
