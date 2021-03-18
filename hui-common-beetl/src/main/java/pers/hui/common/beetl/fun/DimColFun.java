package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.model.FunFieldVal;
import pers.hui.common.beetl.model.casewhen.CaseWhenBinding;
import pers.hui.common.beetl.model.casewhen.WhenVal;
import pers.hui.common.beetl.model.casewhen.WhenValField;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code>DimFun</code>
 * <desc>
 * 描述： 维度字段解析
 * 1. 支持 caseWhen
 * 2. 支持嵌套caseWhen
 * 流程： 查询模板勾选字段分层
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 11:30.
 *
 * @author Gary.Hu
 */
public class DimColFun implements Function {

    private static final String DIM_SYMBOL = "DIM_";
    private static final String CASE_WHEN_SYMBOL = "CASE_WHEN";
    private static final int NO_GROUP_LEN = 3;
    private static final int HAVE_GROUP_LEN = 4;

    /**
     * 形式： #{ dimCol("user_id","用户id","t1.user_id","sqla")} %>
     *
     * @param objects
     * @param context
     * @return
     */
    @Override
    public Object call(Object[] objects, Context context) {
        List<Object> paramsList = Arrays.asList(objects);
        int len = objects.length;
        assert len >= NO_GROUP_LEN;
        if (len == NO_GROUP_LEN) {
            // TODO
        }

        if (len == HAVE_GROUP_LEN) {
            // TODO 考虑union all的场景
        }
        String code = String.valueOf(objects[0]);
        assert code != null;
        // 设置到全局变量 key = DIM_${code}
        String key = DIM_SYMBOL.concat(code);
        FunFieldVal funFieldVal = FunFieldVal.builder()
                .code(String.valueOf(paramsList.get(0)))
                .comment(String.valueOf(paramsList.get(1)))
                .val(String.valueOf(paramsList.get(2)))
                .build();
        context.set(key, funFieldVal);
        return caseWhenHandle(code, context);
    }


    /**
     * caseWhen处理，从全局变量获取到是否需要转换caseWhen字段
     * 1. case when col = 'x'
     * 2. case when col1 = '2' and col2 > 10
     *
     * @param code
     * @param context
     */
    @SuppressWarnings("unchecked")
    private String caseWhenHandle(String code, Context context) {
        Map<String, Object> globalVar = context.globalVar;
        Map<String, CaseWhenBinding> valBindingMap = (Map<String, CaseWhenBinding>) globalVar.get(CASE_WHEN_SYMBOL);
        if (null == valBindingMap || !valBindingMap.containsKey(code)) {
            return code;
        }
        CaseWhenBinding caseWhenBinding = valBindingMap.get(code);
        return recursion(caseWhenBinding);
    }

    private String recursion(CaseWhenBinding caseWhenBinding) {
        List<WhenVal> caseWhenValList = caseWhenBinding.getWhenValList();
        StringBuilder whenThen = new StringBuilder();
        for (WhenVal caseWhenVal : caseWhenValList) {
            List<WhenValField> whenValFieldList = caseWhenVal.getWhenValFieldList();
            List<String> whenValList = whenValFieldList.stream().map(whenField -> {
                String code = whenField.getCode();
                String symbol = whenField.getSymbol();
                String val = whenField.getVal();
                return String.join(" ", code, symbol, val);
            }).collect(Collectors.toList());
            String whenVal = String.join(" and ", whenValList);
            String thenVal = "'" + caseWhenVal.getThenVal() + "'";
            CaseWhenBinding childCaseWhenBinding = caseWhenVal.getChildCaseWhenBinding();
            if (null != childCaseWhenBinding) {
                thenVal = recursion(childCaseWhenBinding);
                System.out.println(thenVal);
            }
            whenThen.append(String.format(" when %s then %s \n", whenVal, thenVal));
        }
        String elseVal = "'" + caseWhenBinding.getElseVal() + "'";

        String code = caseWhenBinding.getCode();
        String alias = "as " + code;
        if (null == code) {
            alias = "";
        }
        return String.format("case %s else %s end %s", whenThen, elseVal, alias);
    }
}
