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
public class KpiColFun implements Function {

    private static final String KPI_SYMBOL = "KPI_";

    /**
     * 形式： #{kpiCol("kpi1","消费总金额","sum(t1.amount)")}
     *
     * @param objects
     * @param context
     * @return
     */
    @Override
    public Object call(Object[] objects, Context context) {
        List<Object> paramsList = Arrays.asList(objects);
        String code = String.valueOf(objects[0]);
        assert code != null;
        // 设置到全局变量 key = DIM_${code}
        String key = KPI_SYMBOL.concat(code);
        context.set(key, paramsList);
        return code;
    }
}
