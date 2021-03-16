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

    @Override
    public Object call(Object[] params, Context context) {
        for (Object param : params) {
            System.out.println(param);
        }
        return null;
    }
}
