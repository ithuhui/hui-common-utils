package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.model.FunFieldVal;

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
     * @param objects 入参
     * @param context 模板上下文
     * @return 解析成功的字符串
     */
    @Override
    public Object call(Object[] objects, Context context) {
        List<Object> paramsList = Arrays.asList(objects);
        String code = String.valueOf(objects[0]);
        assert code != null;
        // 设置到全局变量 key = DIM_${code}
        String key = KPI_SYMBOL.concat(code);
        FunFieldVal funFieldVal = FunFieldVal.builder()
                .code(String.valueOf(paramsList.get(0)))
                .comment(String.valueOf(paramsList.get(1)))
                .val(String.valueOf(paramsList.get(2)))
                .build();
        context.set(key, funFieldVal);
        return code;
    }
}
