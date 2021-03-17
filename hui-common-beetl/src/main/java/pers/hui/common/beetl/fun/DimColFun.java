package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Arrays;
import java.util.List;

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

    private static final String DIM_SYMBOL = "DIM_";

    /**
     * 形式： <% kpiCol("sqla","grp","组"){} %>
     *
     * @param objects
     * @param context
     * @return
     */
    @Override
    public Object call(Object[] objects, Context context) {
        List<Object> valList = Arrays.asList(objects);
        String code = null;
        int len = objects.length;
        assert len >= 3;
        if (len == 3) {
            code = String.valueOf(valList.get(0));
        }

        if (len == 4) {
            code = String.valueOf(valList.get(1));
        }
        context.set(code, valList);
        return context;
    }
}
