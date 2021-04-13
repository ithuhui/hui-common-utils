package pers.hui.common.beetl.fun;

import org.apache.commons.lang3.StringUtils;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.KpiBinding;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.Collections;
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
public class KpiColFun extends BaseSqlParseFun {
    private static final int ROUTE_OUTPUT_FLAG_LENGTH = 5;
    private static final int ROUTE_OUTPUT_FLAG_INDEX = 4;

    @Override
    FunType defineFunType() {
        return FunType.KPI;
    }

    @Override
    String parse(List<FunVal> funVals, SqlContext sqlContext) {
        FunVal funVal = funVals.get(0);
        String key = funVal.getKey();
        Object[] params = funVal.getOriginVals();
        if (!needOutput(params)) {
            return ParseCons.EMPTY_STR;
        }

        if (sqlContext.notNeededParse(FunType.KPI)) {
            return ParseCons.EMPTY_STR;
        }

        List<KpiBinding> kpiBindings = sqlContext.getBindingMap(FunType.KPI, KpiBinding.class).get(key);

        if (null == kpiBindings || kpiBindings.isEmpty()) {
            return ParseCons.EMPTY_STR;
        }

        StringBuilder parseResult = new StringBuilder();
        for (KpiBinding kpiBinding : kpiBindings) {
            String result = funVal.getVal().concat(",");
            if (StringUtils.isNotBlank(kpiBinding.getExpression())) {
                result = funVal.getVal().concat(" " + kpiBinding.getExpression()).concat(",");
            }
            parseResult.append(result);
        }

        return parseResult.toString();
    }

    @Override
    List<FunVal> genFunVals(Object[] params) {
        String code = String.valueOf(params[0]);
        String comment = String.valueOf(params[1]);
        String val = String.valueOf(params[2]);
        String group = String.valueOf(params[3]);
        String key = ParseUtils.keyGen(group, code);
        FunVal funVal = FunVal.builder()
                .originVals(params)
                .key(key)
                .val(val)
                .comment(comment)
                .code(code)
                .group(group)
                .build();

        return Collections.singletonList(funVal);
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


}
