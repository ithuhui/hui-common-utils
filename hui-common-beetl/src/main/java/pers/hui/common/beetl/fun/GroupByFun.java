package pers.hui.common.beetl.fun;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.Binding;
import pers.hui.common.beetl.binding.DimBinding;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <code>GroupByFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 11:31.
 *
 * @author Gary.Hu
 */
public class GroupByFun implements Function {

    private static final String LOW_AS_SYMBOL = " as ";
    private static final String LOW_AS_SYMBOL2 = "'as";
    private static final String UP_AS_SYMBOL = " AS ";
    private static final String UP_AS_SYMBOL2 = "'AS";

    private static final String SPACE = " ";

    /**
     * #{ groupBy("group")} %>
     *
     * @param params  入参
     * @param context 模板上下文
     * @return 解析成功的字符串
     */
    @Override
    public Object call(Object[] params, Context context) {
        SqlContext sqlContext = SqlContext.instance(context);
        String group = String.valueOf(params[0]);
        String key = ParseUtils.keyGen(FunType.GROUP_BY, group);
        FunVal funVal = FunVal.builder()
                .originVals(params)
                .key(key)
                .group(group)
                .build();
        sqlContext.addFunVal(FunType.GROUP_BY, funVal);

        if (!sqlContext.needParse(FunType.DIM)) {
            return ParseCons.EMPTY_STR;
        }

        return parse(sqlContext, group);
    }

    private String parse(SqlContext sqlContext, String group) {
        Set<String> allDimCodes = getAllDimCodes(group, sqlContext);
        Map<String, FunVal> parseFunValMap = sqlContext.getParseFunValMap(FunType.DIM);

        String parse = allDimCodes.stream()
                .map(code -> {
                    String key = ParseUtils.keyGen(group,code);
                    return parseFunValMap.get(key);
                })
                .filter(Objects::nonNull)
                .filter(f -> {
                    String resVal = f.getParseVal();
                    if (resVal.contains(UP_AS_SYMBOL) && resVal.replaceAll(SPACE, ParseCons.EMPTY_STR).contains(UP_AS_SYMBOL2)) {
                        return false;
                    }
                    return !resVal.contains(LOW_AS_SYMBOL) || !resVal.replaceAll(SPACE, ParseCons.EMPTY_STR).contains(LOW_AS_SYMBOL2);
                })
                .map(FunVal::getParseVal)
                .map(resVal -> {
                    int index = resVal.lastIndexOf(",");
                    if (index != -1) {
                        resVal = resVal.substring(0, index);
                    }
                    if (resVal.contains(UP_AS_SYMBOL)) {
                        resVal = resVal.substring(0, resVal.lastIndexOf(UP_AS_SYMBOL));
                    } else if (resVal.contains(LOW_AS_SYMBOL)) {
                        resVal = resVal.substring(0, resVal.lastIndexOf(LOW_AS_SYMBOL));
                    }
                    return resVal;
                })
                .collect(Collectors.joining(","));
        if (StringUtils.isBlank(parse)) {
            return ParseCons.EMPTY_STR;
        }
        return parse;
    }

    /**
     * 获取所有dim字段的
     *
     * @param group
     * @param sqlContext
     * @return
     */
    private Set<String> getAllDimCodes(String group, SqlContext sqlContext) {
        Map<String, Binding> bindingInfoMap = sqlContext.getBindingInfoMap(FunType.DIM);

        return bindingInfoMap
                .values()
                .stream()
                .map(bindingInfo -> (DimBinding) bindingInfo)
                .filter(bindingInfo -> bindingInfo.getGroup().equals(group))
                .map(DimBinding::getCode)
                .collect(Collectors.toSet());
    }
}
