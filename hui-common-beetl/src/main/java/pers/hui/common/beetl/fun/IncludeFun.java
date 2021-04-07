package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.*;
import pers.hui.common.beetl.binding.*;
import pers.hui.common.beetl.utils.ParseUtils;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static final String TEMPLATE_BASE = "base";
    public static final String TEMPLATE_GLOBAL_VAL = "global_val";

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
        SqlContext sqlContext = SqlContext.instance(context);
        String type = String.valueOf(params[0]);
        String contentCode = String.valueOf(params[1]);

        String key = ParseUtils.keyGen(contentCode);


        if (type.equalsIgnoreCase(TEMPLATE_BASE)) {
            Map<String, Binding> bindingInfoMap = sqlContext.getBindingInfoMap(FunType.INCLUDE_BASE);
            IncludeBinding bindingInfo = (IncludeBinding) bindingInfoMap.get(ParseUtils.keyGen(contentCode));
            // 保存标签函数值
            FunVal funVal = FunVal.builder()
                    .funType(FunType.INCLUDE_BASE)
                    .code(contentCode)
                    .key(key)
                    .build();
            sqlContext.addFunVal(FunType.INCLUDE_BASE, funVal);
            if (!sqlContext.needParse(FunType.INCLUDE_BASE)) {
                return ParseCons.EMPTY_STR;
            }


            try {
                Map<String, FunVal> funValMap = SqlParseUtils.getFunValMap(bindingInfo.getIncludeContent());
                // 当发现标签函数的时候 需要重新进行解析
                if (null != funValMap) {
                    // 嵌套的include函数元信息记录
                    funValMap.values().forEach(val -> sqlContext.addFunVal(val.getFunType(), val));
                    // 对嵌套的includeContent解析一次
                    String parseV1 = parseWithFun(bindingInfo.getIncludeContent(), sqlContext);
                    return parse(contentCode, parseV1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ParseCons.EMPTY_STR;
            }
            if (!sqlContext.needParse(FunType.INCLUDE_BASE)) {
                return ParseCons.EMPTY_STR;
            }
            return parse(contentCode, bindingInfo.getIncludeContent());
        }


        // 全局变量的情况下 直接替换文本
        if (type.equalsIgnoreCase(TEMPLATE_GLOBAL_VAL)) {
            Map<String, Binding> bindingInfoMap = sqlContext.getBindingInfoMap(FunType.INCLUDE_GLOBAL_VAL);
            IncludeBinding bindingInfo = (IncludeBinding) bindingInfoMap.get(ParseUtils.keyGen(contentCode));
            // 保存标签函数值
            FunVal funVal = FunVal.builder()
                    .funType(FunType.INCLUDE_GLOBAL_VAL)
                    .code(contentCode)
                    .key(key)
                    .build();
            sqlContext.addFunVal(FunType.INCLUDE_GLOBAL_VAL, funVal);
            if (!sqlContext.needParse(FunType.INCLUDE_GLOBAL_VAL)) {
                return ParseCons.EMPTY_STR;
            }
            return bindingInfo.getIncludeContent();
        }

        return ParseCons.EMPTY_STR;
    }

    private String parse(String code, String includeContent) {
        return String.format("%s as ( %s )", code, includeContent);
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
        bindingInfo.setDimBindingInfos(sqlContext.getBindingInfoMap(FunType.DIM).values().stream().map(x -> (DimBinding) x).collect(Collectors.toList()));
        bindingInfo.setKpiBindings(sqlContext.getBindingInfoMap(FunType.KPI).values().stream().map(x -> (KpiBinding) x).collect(Collectors.toList()));
        bindingInfo.setWhereBindings(sqlContext.getBindingInfoMap(FunType.WHERE).values().stream().map(x -> (WhereBinding) x).collect(Collectors.toList()));
        try {
            return SqlParseUtils.renderWithBinding(includeContent, bindingInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ParseCons.EMPTY_STR;
    }
}
