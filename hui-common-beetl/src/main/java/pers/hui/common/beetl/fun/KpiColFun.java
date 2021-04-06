package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.KpiBinding;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.Map;

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
    private static final int ROUTE_OUTPUT_FLAG_LENGTH = 5;
    private static final int ROUTE_OUTPUT_FLAG_INDEX = 4;
    
    /**
     * 形式： #{kpi("kpi1","消费总金额","sum(t1.amount)","sqla",true)}
     *
     * @param params  入参
     * @param context 模板上下文
     * @return 解析成功的字符串
     */
    @Override
    public Object call(Object[] params, Context context) {
        SqlContext sqlContext = SqlContext.instance(context);

        String code = String.valueOf(params[0]);
        String comment = String.valueOf(params[1]);
        String val = String.valueOf(params[2]);
        String group = String.valueOf(params[3]);
        String key = ParseUtils.keyGen(group, code);
        Boolean isOutput = true;
        if (params.length == 5) {
            isOutput = (Boolean) params[4];
        }

        FunVal funVal = FunVal.builder()
                .originVals(params)
                .key(key)
                .val(val)
                .comment(comment)
                .code(code)
                .group(group)
                .build();

        sqlContext.addFunVal(FunType.DIM, funVal);

        if (!needOutput(params)) {
            return ParseCons.EMPTY_STR;
        }

        if (!sqlContext.needParse(FunType.DIM)) {
            return ParseCons.EMPTY_STR;
        }
        return parse(key, sqlContext);
    }

    /**
     * 是否需要输出字段
     *
     * @param params 解析入参
     * @return 是否需要输出字段
     */
    private boolean needOutput(Object[] params) {
        boolean isOutput = true;
        // 需要路由的情况
        if (params.length == ROUTE_OUTPUT_FLAG_LENGTH) {
            isOutput = (Boolean) params[ROUTE_OUTPUT_FLAG_INDEX];
        }
        return isOutput;
    }

    private String parse(String key, SqlContext sqlContext) {
        KpiBinding kpiBinding = (KpiBinding) sqlContext.getBindingInfoMap(FunType.KPI).get(key);
        if (null == kpiBinding) {
            return ParseCons.EMPTY_STR;
        }
        Map<String, FunVal> parseFunValMap = sqlContext.getParseFunValMap(FunType.KPI);
        FunVal funVal = parseFunValMap.get(key);
        return funVal.getKey();
    }

}
