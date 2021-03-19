package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.model.FunFieldVal;

import java.util.Arrays;
import java.util.List;

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
//        GroupInfo groupInfo = (GroupInfo) ctx.getGlobal(DIM_SYMBOL + group);
//        List<String> dimCodeList = groupInfo.getDimCodeList();
        StringBuilder res = new StringBuilder("\ngroup by \n");
        List<String> dimCodeList = Arrays.asList("user_id", "order_id");
        for (String dimCode : dimCodeList) {
            FunFieldVal funFieldVal = (FunFieldVal) ctx.getGlobal(DIM_SYMBOL + dimCode);
            res.append(funFieldVal.getResVal());
            res.append(",");
        }
        return res;
    }
}
