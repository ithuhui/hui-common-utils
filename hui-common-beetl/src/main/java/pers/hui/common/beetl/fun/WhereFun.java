package pers.hui.common.beetl.fun;

import org.apache.commons.lang3.StringUtils;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.WhereBinding;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <code>WhereFun</code>
 * <desc>
 * 描述：从context获取用户定义的where字段 #{where("group")}
 * <desc/>
 * <b>Creation Time:</b> 2021/3/16 23:06.
 *
 * @author Ken.Hu
 */
public class WhereFun extends BaseSqlParseFun {

    private static final int WHERE_BINDINGS_SIZE = 1;

    @Override
    FunType defineFunType() {
        return FunType.WHERE;
    }

    @Override
    String parse(List<FunVal> funVals, SqlContext sqlContext) {
        Map<String, List<WhereBinding>> bindingMap = sqlContext.getBindingMap(FunType.WHERE,WhereBinding.class);
        FunVal funVal = funVals.get(0);
        String group = funVal.getGroup();
        if (Objects.isNull(bindingMap)) {
            return ParseCons.EMPTY_STR;
        }

        List<WhereBinding> whereBindings = bindingMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(bindingInfo -> group.equals(bindingInfo.getGroup()))
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
        return parseFromExpression(whereInfos, expression);
    }

    @Override
    List<FunVal> genFunVals(Object[] params) {
        return Collections.emptyList();
    }

    private String parseFromExpression(List<WhereBinding.WhereInfo> whereInfos, String expression) {
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
