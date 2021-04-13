package pers.hui.common.beetl.fun;

import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.DimBinding;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <code>DimFun</code>
 * <desc>
 * 描述： 维度字段解析
 * 1. 支持 caseWhen
 * 2. 支持嵌套caseWhen
 * 流程： 查询模板勾选字段分层
 *  #{ dim("user_id","用户id","t1.user_id","sqla",true)} %>
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 11:30.
 *
 * @author Gary.Hu
 */
public class DimColFun extends BaseSqlParseFun{

    private static final int ROUTE_OUTPUT_FLAG_LENGTH = 5;
    private static final int ROUTE_OUTPUT_FLAG_INDEX = 4;


    @Override
    FunType defineFunType() {
        return FunType.DIM;
    }

    @Override
    String parse(List<FunVal> funVals, SqlContext sqlContext) {
        FunVal funVal = funVals.get(0);
        Object[] params = funVal.getOriginVals();
        if (!needOutput(params)) {
            return ParseCons.EMPTY_STR;
        }

        if (sqlContext.notNeededParse(FunType.DIM)) {
            return ParseCons.EMPTY_STR;
        }
        DimBinding dimBinding = (DimBinding) sqlContext.getBindingMap(FunType.DIM).get(funVal.getKey());
        if (null == dimBinding){
            return ParseCons.EMPTY_STR;
        }
        DimBinding.CaseWhenBinding caseWhenBinding = dimBinding.getCaseWhenBinding();
        if (null == caseWhenBinding) {
            return funVal.getVal().concat(",");
        }
        // 保存解析后的值
        return recursion(caseWhenBinding).concat(",");
    }

    @Override
    List<FunVal> genFunVals(Object[] params) {
        String code = String.valueOf(params[0]);
        String comment = String.valueOf(params[1]);
        String val = String.valueOf(params[2]);
        String group = String.valueOf(params[3]);
        String key = ParseUtils.keyGen(group, code);
        // 构建解析前的信息
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

    /**
     * 递归构造caseWhen语句
     *
     * @param caseWhenBinding caseWhen绑定信息
     * @return 返回解析后的值
     */
    private String recursion(DimBinding.CaseWhenBinding caseWhenBinding) {
        List<DimBinding.WhenVal> caseWhenValList = caseWhenBinding.getWhenValList();
        StringBuilder whenThen = new StringBuilder();
        for (DimBinding.WhenVal caseWhenVal : caseWhenValList) {
            List<DimBinding.WhenValField> whenValFieldList = caseWhenVal.getWhenValFieldList();
            List<String> whenValList = whenValFieldList.stream().map(whenField -> {
                String code = whenField.getCode();
                String symbol = whenField.getSymbol();
                String val = whenField.getVal();
                return String.join(" ", code, symbol, val);
            }).collect(Collectors.toList());
            String whenVal = String.join(" and ", whenValList);
            String thenVal = "'" + caseWhenVal.getThenVal() + "'";
            DimBinding.CaseWhenBinding childCaseWhenBinding = caseWhenVal.getChildCaseWhenBinding();
            if (null != childCaseWhenBinding) {
                thenVal = recursion(childCaseWhenBinding);
            }
            whenThen.append(String.format(" when %s then %s \n", whenVal, thenVal));
        }
        String elseVal = "'" + caseWhenBinding.getElseVal() + "'";
        String value = caseWhenBinding.getAlise();
        String alias = "as " + value;
        if (null == value) {
            alias = "";
        }
        return String.format("case %s else %s end %s", whenThen, elseVal, alias);
    }

}
