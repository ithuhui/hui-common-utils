package pers.hui.common.beetl.fun;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.Binding;
import pers.hui.common.beetl.binding.WhereBinding;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    private static final int WHERE_BINDINGS_SIZE = 1;

    /**
     * way1: 全部字段都显示声明 ： #{whereField("code", "comment", "val", "group")}
     * way2: 从context获取用户定义的where字段 #{where("group")}
     * way3: 语法树解析
     *
     * @param params  入参
     * @param context 模板上下文
     * @return 解析成功的字符串
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object call(Object[] params, Context context) {
        SqlContext sqlContext = SqlContext.instance(context);
        String group = String.valueOf(params[0]);
        Map<String, Binding> bindingInfoMap = sqlContext.getBindingInfoMap(FunType.WHERE);
        if (Objects.isNull(bindingInfoMap)) {
            return ParseCons.EMPTY_STR;
        }

        List<WhereBinding> whereBindings = bindingInfoMap
                .values()
                .stream()
                .map(bindingInfo -> (WhereBinding) bindingInfo
                ).filter(bindingInfo -> group.equals(bindingInfo.getGroup()))
                .collect(Collectors.toList());

        if (whereBindings.size() != WHERE_BINDINGS_SIZE) {
            return ParseCons.EMPTY_STR;
        }

        WhereBinding whereBinding = whereBindings.get(0);
        String expression = whereBinding.getExpression();
        List<WhereBinding.WhereInfo> whereInfos = whereBinding.getWhereInfos();

        if (StringUtils.isBlank(expression)) {
            return defaultParse(whereInfos);
        }
        return parse(whereInfos, expression);
    }


    private String parse(List<WhereBinding.WhereInfo> whereInfos, String expression) {
        for (WhereBinding.WhereInfo whereInfo : whereInfos) {
            String id = whereInfo.getId();
            expression = expression.replace(id, "id-".concat(id));
        }

        for (WhereBinding.WhereInfo whereInfo : whereInfos) {
            String whereVal = genWhereVal(whereInfo);
            if (StringUtils.isNotBlank(whereInfo.getId())) {
                expression = expression.replace("id-".concat(whereInfo.getId()), whereVal);
            } else {
                expression = whereVal;
            }
        }
        return expression;
    }

    private String defaultParse(List<WhereBinding.WhereInfo> whereInfos) {
        return whereInfos.stream()
                .map(this::genWhereVal)
                .collect(Collectors.joining(" and "));
    }

    private String genWhereVal(WhereBinding.WhereInfo whereInfo) {
        return String.format("%s %s '%s'", whereInfo.getVal(), whereInfo.getOperator(), whereInfo.getOperatorVal());
    }
}
