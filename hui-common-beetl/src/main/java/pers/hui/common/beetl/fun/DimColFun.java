package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.model.CaseWhenBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <code>DimFun</code>
 * <desc>
 * 描述： 维度字段解析
 * 1. 支持 cashwhen
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
        context.set(key, paramsList);
        return caseWhenHandle(code, context);
    }


    /**
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
        List<CaseWhenBinding.CaseWhenVal> caseWhenValList = caseWhenBinding.getCaseWhenValList();
        StringBuilder whenThen = new StringBuilder();
        for (CaseWhenBinding.CaseWhenVal caseWhenVal : caseWhenValList) {
            List<String> whenValList = caseWhenVal.getWhenValList();
            String whenVal = String.join(" and ", whenValList);
            String thenVal = caseWhenVal.getThenVal();
            List<CaseWhenBinding> childCaseWhenBindingList = caseWhenVal.getChildCaseWhenBindingList();
            if (null != childCaseWhenBindingList && !childCaseWhenBindingList.isEmpty()) {
                for (CaseWhenBinding val : childCaseWhenBindingList) {
                    thenVal = recursion(val);
                    System.out.println(thenVal);
                }
            }
            whenThen.append(String.format(" when %s then %s \n", whenVal, thenVal));
        }
        String elseVal = caseWhenBinding.getElseVal();
        return String.format("case %s else %s end as %s", whenThen, elseVal, caseWhenBinding.getCode());
    }
}
