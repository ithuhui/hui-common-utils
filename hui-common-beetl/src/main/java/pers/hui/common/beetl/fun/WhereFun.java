package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.model.WhereBinding;
import pers.hui.common.beetl.model.WhereInfo;
import pers.hui.common.beetl.model.casewhen.CaseWhenBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code>WhereFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/16 23:06.
 *
 * @author Ken.Hu
 */
public class WhereFun implements Function {

    private static final String WHERE_SYMBOL = "WHERE";

    /**
     * way1: 全部字段都显示声明 ： #{whereField("code", "comment", "val", "group")}
     * way2: 从context获取用户定义的where字段 #{where("group")}
     *
     * @param params  入参
     * @param context 模板上下文
     * @return 解析成功的字符串
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object call(Object[] params, Context context) {
        String group = String.valueOf(params[0]);
        WhereInfo whereInfo = (WhereInfo) context.globalVar.get(WHERE_SYMBOL.concat("_").concat(group));
        String whereExpression = whereInfo.getExpression();
        List<WhereBinding> whereBindings = whereInfo.getWhereBindings();
        for (WhereBinding whereBinding : whereBindings) {
            String expression;
            if (whereBinding.getSymbol().equals("between")) {
                expression = String.format("%s between(%s,%s)", whereBinding.getCode(), whereBinding.getVal1(), whereBinding.getVal2());
            } else {
                expression = String.format("%s %s %s", whereBinding.getCode(), whereBinding.getSymbol(), whereBinding.getVal1());
            }
            whereExpression = whereExpression.replace(whereBinding.getOrder(), expression);
        }
        return whereExpression;
    }
}
