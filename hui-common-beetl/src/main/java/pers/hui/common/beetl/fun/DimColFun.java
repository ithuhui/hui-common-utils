package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * <code>DimFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 11:30.
 *
 * @author Gary.Hu
 */
public class DimColFun implements Function {
    @Override
    public Object call(Object[] objects, Context context) {
        for (Object object : objects) {
            System.out.println(object);
        }
        return null;
    }
}
