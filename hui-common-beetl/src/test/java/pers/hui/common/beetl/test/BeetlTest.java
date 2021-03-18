package pers.hui.common.beetl.test;

import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.junit.Test;
import pers.hui.common.beetl.BeetlCore;
import pers.hui.common.beetl.model.CaseWhenBinding;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>BeetlTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 14:28.
 *
 * @author Gary.Hu
 */
public class BeetlTest {

    @Test
    public void test() throws IOException {
        String templateStr = "select\n" +
                "   #{ dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}\n" +
                "from test t1";
        GroupTemplate groupTemplate = BeetlCore.groupTemplateInit();
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        template.binding("name", "beetl");
        //渲染结果
        String result = template.render();
        Context ctx = template.getCtx();
        Map<String, Object> globalVar = ctx.globalVar;
        System.out.println(globalVar.toString());
        System.out.println("---------- sql ----------");
        System.out.println(result);
    }

    @Test
    public void getFiledTest() throws IOException {
        String templateStr = "select\n" +
                "   #{dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}\n" +
                "from test t1";
        Map<String, Object> fieldValue = BeetlCore.getFieldValue(templateStr);

        for (String key : fieldValue.keySet()) {
            System.out.println(key + ":" + fieldValue.get(key));
        }
    }

    @Test
    public void caseWhenTest() throws IOException {

        CaseWhenBinding caseWhenBinding2 = new CaseWhenBinding();
        caseWhenBinding2.setCode("user_id");
        caseWhenBinding2.setValue("t1.user_id");
        CaseWhenBinding.CaseWhenVal caseWhenVal2 = new CaseWhenBinding.CaseWhenVal();
        caseWhenVal2.setWhenValList(Arrays.asList("CP_SALES_EXCL_VAT >= LP_SALES_EXCL_VAT"));
        caseWhenVal2.setThenVal("'BRAND INCREASE'");
        caseWhenBinding2.setElseVal("'BRAND DECREASE'");
        caseWhenBinding2.setCaseWhenValList(Arrays.asList(caseWhenVal2));


        CaseWhenBinding caseWhenBinding = new CaseWhenBinding();
        caseWhenBinding.setCode("user_id");
        caseWhenBinding.setValue("t1.user_id");
        CaseWhenBinding.CaseWhenVal caseWhenVal = new CaseWhenBinding.CaseWhenVal();
        caseWhenVal.setWhenValList(Arrays.asList("LP_FLAG > '10'", "CP_FLAG < '20'"));
        caseWhenVal.setThenVal("'ROOT'");
        caseWhenVal.setChildCaseWhenBindingList(Arrays.asList(caseWhenBinding2));
        caseWhenBinding.setElseVal("'NA'");
        caseWhenBinding.setCaseWhenValList(Arrays.asList(caseWhenVal));

        HashMap<String, CaseWhenBinding> caseWhenMap = new HashMap<>();
        caseWhenMap.put(caseWhenBinding.getCode(), caseWhenBinding);



        String templateStr = "select\n" +
                "   #{dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}\n" +
                "from test t1";
        GroupTemplate groupTemplate = BeetlCore.groupTemplateInit();
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        template.getCtx().set("CASE_WHEN", caseWhenMap);
        //渲染结果
        String result = template.render();
        Context ctx = template.getCtx();
        Map<String, Object> globalVar = ctx.globalVar;
        System.out.println(globalVar.toString());
        System.out.println("---------- sql ----------");
        System.out.println(result);
    }
}
