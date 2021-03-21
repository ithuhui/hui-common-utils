package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.model.FunFieldVal;
import pers.hui.common.beetl.model.GroupInfo;
import pers.hui.common.beetl.model.SqlKey;
import pers.hui.common.beetl.model.info.GroupBy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    private static final String DIM_SYMBOL = "DIM_";

    /**
     * 形式： #{ groupBy("group")} %>
     * @param params 入参
     * @param ctx 模板上下文
     * @return 解析成功的字符串
     */
    @Override
    public Object call(Object[] params, Context ctx) {
        String group = String.valueOf(params[0]);
        GroupBy groupBy = (GroupBy) ctx.getGlobal(SqlKey.GROUP_BY.name());
        Map<String, Set<String>> groupBindingMap = groupBy.getGroupBindingMap();
        Set<String> dimCodeSet = groupBindingMap.get(group);
        StringBuilder res = new StringBuilder("\ngroup by \n");
        String groupByVal = dimCodeSet.stream()
                .map(dimCode -> (FunFieldVal) ctx.getGlobal(DIM_SYMBOL + dimCode))
                .map(FunFieldVal::getResVal)
                .collect(Collectors.joining(","));
        return res.append(groupByVal);
    }
}
