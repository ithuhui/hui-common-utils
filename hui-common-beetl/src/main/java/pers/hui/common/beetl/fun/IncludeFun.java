package pers.hui.common.beetl.fun;

import pers.hui.common.beetl.*;
import pers.hui.common.beetl.binding.*;
import pers.hui.common.beetl.utils.ParseUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code>IncludeFun</code>
 * <desc>
 * 描述：include标签函数
 * #{include("base","t_user")}
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 14:21.
 *
 * @author Gary.Hu
 */
public class IncludeFun extends BaseSqlParseFun {
    public static final String TEMPLATE_BASE = "base";
    public static final String TEMPLATE_GLOBAL_VAL = "global_val";

    @Override
    FunType defineFunType() {
        return null;
    }

    @Override
    String parse(List<FunVal> funVals, SqlContext sqlContext) {
        FunVal funVal = funVals.get(0);
        Object[] params = funVal.getOriginVals();
        String type = String.valueOf(params[0]);

        if (sqlContext.notNeededParse(FunType.INCLUDE)) {
            return ParseCons.EMPTY_STR;
        }
        // 全局变量的情况下 直接替换文本
        if (type.equalsIgnoreCase(TEMPLATE_GLOBAL_VAL)) {
            List<IncludeBinding> bindingInfos = sqlContext.getBindingInfo(FunType.INCLUDE, funVal.getKey(), IncludeBinding.class);
            IncludeBinding includeBinding = bindingInfos.get(0);
            return includeBinding.getIncludeContent();
        }

        if (type.equalsIgnoreCase(TEMPLATE_BASE)) {
            List<IncludeBinding> bindingInfos = sqlContext.getBindingInfo(FunType.INCLUDE, funVal.getKey(), IncludeBinding.class);
            IncludeBinding includeBinding = bindingInfos.get(0);

            try {
                List<Map<String, FunVal>> funValMap = SqlParseUtils.getFunValMap(includeBinding.getIncludeContent());
                // 当发现标签函数的时候 需要重新进行解析
                if (null != funValMap) {
                    // 对嵌套的includeContent解析一次
                    String parseV1 = parseWithFun(includeBinding.getIncludeContent(), sqlContext);
                    return defaultParse(includeBinding.getCode(), parseV1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ParseCons.EMPTY_STR;
            }

            return defaultParse(includeBinding.getCode(), includeBinding.getIncludeContent());
        }

        return ParseCons.EMPTY_STR;
    }

    private String defaultParse(String code, String includeContent) {
        return String.format("%s as ( %s )", code, includeContent);
    }


    @Override
    List<FunVal> genFunVals(Object[] params) {
        String contentCode = String.valueOf(params[1]);
        String key = ParseUtils.keyGen(contentCode);

        FunVal funVal = FunVal.builder()
                .funType(FunType.INCLUDE)
                .code(contentCode)
                .key(key)
                .originVals(params)
                .build();

        return Collections.singletonList(funVal);
    }



    /**
     * 当基础模板存在标签函数时
     *
     * @param includeContent 文本内容
     * @param sqlContext     sql全局变量上下文
     * @return
     */
    @SuppressWarnings("unchecked")
    private String parseWithFun(String includeContent, SqlContext sqlContext) {

        BindingInfo bindingInfo = new BindingInfo();
        List<Binding> bindings = bindingInfo.getBindings();
        bindings.addAll(sqlContext.getBindingMap(FunType.DIM).values().stream().map(x -> (DimBinding) x).collect(Collectors.toList()));
        bindings.addAll(sqlContext.getBindingMap(FunType.KPI).values().stream().map(x -> (KpiBinding) x).collect(Collectors.toList()));
        bindings.addAll(sqlContext.getBindingMap(FunType.WHERE).values().stream().map(x -> (WhereBinding) x).collect(Collectors.toList()));
        try {
            return SqlParseUtils.renderWithBinding(includeContent, bindingInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ParseCons.EMPTY_STR;
    }
}
