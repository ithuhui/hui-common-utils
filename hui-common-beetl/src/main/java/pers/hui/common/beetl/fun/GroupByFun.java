package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.List;
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

        FunVal funVal = FunVal.builder()
                .originVals(params)
                .key(ParseUtils.keyGen(FunType.GROUP_BY, group))
                .group(group)
                .build();

        if (sqlContext.needParse(FunType.GROUP_BY)) {
            Set<String> allDimCodes = getAllDimCodes(group, sqlContext);

            return ParseCons.EMPTY_STR;
        } else {
            return ParseCons.EMPTY_STR;
        }
    }

    private Set<String> getAllDimCodes(String group, SqlContext sqlContext) {
        List<FunVal> funVals = sqlContext.getFunVals(FunType.DIM);
        return funVals.stream()
                .filter(funVal -> funVal.getGroup().equals(group))
                .map(FunVal::getCode).collect(Collectors.toSet());
    }
}
