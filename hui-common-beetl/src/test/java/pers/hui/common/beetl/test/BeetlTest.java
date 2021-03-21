package pers.hui.common.beetl.test;

import com.alibaba.fastjson.JSON;
import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.junit.Test;
import pers.hui.common.beetl.BeetlSqlParseUtils;
import pers.hui.common.beetl.model.IncludeBinding;
import pers.hui.common.beetl.model.CaseWhenBinding;
import pers.hui.common.beetl.model.info.BindingInfo;
import pers.hui.common.beetl.model.info.Where;

import java.io.IOException;
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
        GroupTemplate groupTemplate = BeetlSqlParseUtils.groupTemplateInit();
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
        Map<String, Object> fieldValue = BeetlSqlParseUtils.getFieldValue(templateStr);

        for (String key : fieldValue.keySet()) {
            System.out.println(key + ":" + fieldValue.get(key));
        }
    }

    @Test
    public void caseWhenTest() throws IOException {

        //language=JSON
        String json = "{\n" +
                "  \"code\": \"user_id\",\n" +
                "  \"elseVal\": \"NA\",\n" +
                "  \"whenValList\": {\n" +
                "    \"whenValFieldList\": [\n" +
                "      {\n" +
                "        \"code\": \"LP_FLAG\",\n" +
                "        \"symbol\": \">\",\n" +
                "        \"val\": \"0\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\": \"CP_FLAG\",\n" +
                "        \"symbol\": \">\",\n" +
                "        \"val\": \"0\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"thenVal\": null,\n" +
                "    \"childCaseWhenBinding\": {\n" +
                "      \"code\": null,\n" +
                "      \"whenValList\": {\n" +
                "        \"whenValFieldList\": [\n" +
                "          {\n" +
                "            \"code\": \"CP_SALES_EXCL_VAT\",\n" +
                "            \"symbol\": \">\",\n" +
                "            \"val\": \"LP_SALES_EXCL_VAT\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"thenVal\": \"BRAND INCREAS\",\n" +
                "        \"childCaseWhenBinding\": null\n" +
                "      },\n" +
                "      \"elseVal\": \"BRAND DECREASE\"\n" +
                "    " +
                "}" +
                "\n" +
                "  }\n" +
                "}";

        CaseWhenBinding caseWhenBinding = JSON.parseObject(json, CaseWhenBinding.class);


        String templateStr = "select\n" +
                "   #{dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}\n" +
                "from test t1";
        GroupTemplate groupTemplate = BeetlSqlParseUtils.groupTemplateInit();
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        HashMap<Object, Object> caseWhenMap = new HashMap<>();
        caseWhenMap.put(caseWhenBinding.getCode(), caseWhenBinding);
        template.getCtx().set("CASE_WHEN", caseWhenMap);
        //渲染结果
        String result = template.render();
        Context ctx = template.getCtx();
        Map<String, Object> globalVar = ctx.globalVar;
        System.out.println(globalVar.toString());
        System.out.println("---------- sql ----------");
        System.out.println(result);
    }

    @Test
    public void whereTest() throws IOException {
        //language=JSON
        String json = "{\n" +
                "  \"expression\": \"1 AND 2 OR (3 AND 4)\",\n" +
                "  \"group\": \"group\",\n" +
                "  \"whereBindings\": [\n" +
                "    {\n" +
                "      \"code\": \"amount\",\n" +
                "      \"order\": \"1\",\n" +
                "      \"symbol\": \">\",\n" +
                "      \"val1\": \"10\",\n" +
                "      \"val2\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"to_date(txn.business_dt_key,'yyyymmdd')\",\n" +
                "      \"order\": \"2\",\n" +
                "      \"symbol\": \"bewteen\",\n" +
                "      \"val1\": \"v.begin_date (+)\",\n" +
                "      \"val2\": \"v.end_date (+)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"txn.transaction_type_name\",\n" +
                "      \"order\": \"3\",\n" +
                "      \"symbol\": \"=\",\n" +
                "      \"val1\": \"Item\",\n" +
                "      \"val2\": \"Item\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"txn.member_sale_flag\",\n" +
                "      \"order\": \"4\",\n" +
                "      \"symbol\": \"=\",\n" +
                "      \"val1\": \"Y\",\n" +
                "      \"val2\": \"Y\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Where where = JSON.parseObject(json, Where.class);

        String templateStr = "select\n" +
                "   #{dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}\n" +
                "from test t1 where 1=1  AND #{where(\"group\")}";

        GroupTemplate groupTemplate = BeetlSqlParseUtils.groupTemplateInit();
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        template.getCtx().set("WHERE_group", where);

        String result = template.render();
        System.out.println(result);

    }

    @Test
    public void dynamicRoute() throws IOException {
        String templateStr = "\n" +
                "<%\n" +
                "var output1 = dim1.out or dim2.out;\n" +
                "println(output1);\n" +
                "println(dim);\n" +
                "var test1 = test('code','comment','val',output1);" +
                "%>\n"+
                "#{test1}";

        GroupTemplate groupTemplate = BeetlSqlParseUtils.groupTemplateInit();
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        template.binding("dim","2");
//        template.getCtx().set("dim", "1");
//        groupTemplate.getSharedVars().put("dim", "1");

        String result = template.render();
        System.out.println("-----------");
        System.out.println(result);
    }

    @Test
    public void include() throws IOException {
        //language=JSON
        String json = "{\n" +
                "  \"code\": \"test\",\n" +
                "  \"includeContent\": \"select * from t_include_test\"\n" +
                "}";
        IncludeBinding includeBinding = JSON.parseObject(json, IncludeBinding.class);


        String templateStr = "#{includeSub(\"base\",\"test\")} \n" +
                "select\n" +
                "   #{dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}\n" +
                "from test t1 where 1=1  }";;
        GroupTemplate groupTemplate = BeetlSqlParseUtils.groupTemplateInit();
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        template.getCtx().set("INCLUDE_base_test", includeBinding);
        //渲染结果
        String result = template.render();
        Context ctx = template.getCtx();
        Map<String, Object> globalVar = ctx.globalVar;
        System.out.println(globalVar.toString());
        System.out.println("---------- sql ----------");
        System.out.println(result);
    }

    @Test
    public void groupTest() throws IOException {
        //language=JSON
        String json = "{\n" +
                "  \"code\": \"user_id\",\n" +
                "  \"elseVal\": \"NA\",\n" +
                "  \"whenValList\": {\n" +
                "    \"whenValFieldList\": [\n" +
                "      {\n" +
                "        \"code\": \"LP_FLAG\",\n" +
                "        \"symbol\": \">\",\n" +
                "        \"val\": \"0\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\": \"CP_FLAG\",\n" +
                "        \"symbol\": \">\",\n" +
                "        \"val\": \"0\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"thenVal\": null,\n" +
                "    \"childCaseWhenBinding\": {\n" +
                "      \"code\": null,\n" +
                "      \"whenValList\": {\n" +
                "        \"whenValFieldList\": [\n" +
                "          {\n" +
                "            \"code\": \"CP_SALES_EXCL_VAT\",\n" +
                "            \"symbol\": \">\",\n" +
                "            \"val\": \"LP_SALES_EXCL_VAT\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"thenVal\": \"BRAND INCREAS\",\n" +
                "        \"childCaseWhenBinding\": null\n" +
                "      },\n" +
                "      \"elseVal\": \"BRAND DECREASE\"\n" +
                "    " +
                "}" +
                "\n" +
                "  }\n" +
                "}";
        CaseWhenBinding caseWhenBinding = JSON.parseObject(json, CaseWhenBinding.class);


        String templateStr = "select\n" +
                "   #{dimCol(\"order_id\",\"订单id\",\"t1.order_id\",\"sqla\")},\n" +
                "   #{dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}" +
                "   \n" +
                "from test t1 #{groupBy(\"sqla\")}";
        GroupTemplate groupTemplate = BeetlSqlParseUtils.groupTemplateInit();
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        HashMap<Object, Object> caseWhenMap = new HashMap<>();
        caseWhenMap.put(caseWhenBinding.getCode(), caseWhenBinding);
        template.getCtx().set("CASE_WHEN", caseWhenMap);
        //渲染结果
        String result = template.render();
        Context ctx = template.getCtx();
        Map<String, Object> globalVar = ctx.globalVar;
        System.out.println(globalVar.toString());
        System.out.println("---------- sql ----------");
        System.out.println(result);

    }

    @Test
    public void totalTest() throws IOException {
        //language=JSON
        String json = "{\n" +
                "  \"include\": {\n" +
                "    \"baseTemplate\": {\n" +
                "      \"map-key-test\": {\n" +
                "        \"code\": \"test\",\n" +
                "        \"includeContent\": \"select * from t_include_test1\"\n" +
                "      },\n" +
                "      \"map-key-test2\": {\n" +
                "        \"code\": \"test2\",\n" +
                "        \"includeContent\": \"select * from t_include_test2\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"globalTemplate\": {\n" +
                "      \"map-key-test\": {\n" +
                "        \"code\": \"test\",\n" +
                "        \"includeContent\": \"#{addDim('','','')},#{addDim('','','')}\"\n" +
                "      },\n" +
                "      \"map-key-test2\": {\n" +
                "        \"code\": \"test2\",\n" +
                "        \"includeContent\": \"#{addDim('','','')},#{addDim('','','')}\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"where\": {\n" +
                "    \"expression\": \"1 AND 2 OR (3 AND 4)\",\n" +
                "    \"group\": \"group\",\n" +
                "    \"whereBindings\": [\n" +
                "      {\n" +
                "        \"code\": \"amount\",\n" +
                "        \"order\": \"1\",\n" +
                "        \"symbol\": \">\",\n" +
                "        \"val1\": \"10\",\n" +
                "        \"val2\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\": \"to_date(txn.business_dt_key,'yyyymmdd')\",\n" +
                "        \"order\": \"2\",\n" +
                "        \"symbol\": \"bewteen\",\n" +
                "        \"val1\": \"v.begin_date (+)\",\n" +
                "        \"val2\": \"v.end_date (+)\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\": \"txn.transaction_type_name\",\n" +
                "        \"order\": \"3\",\n" +
                "        \"symbol\": \"=\",\n" +
                "        \"val1\": \"Item\",\n" +
                "        \"val2\": \"Item\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\": \"txn.member_sale_flag\",\n" +
                "        \"order\": \"4\",\n" +
                "        \"symbol\": \"=\",\n" +
                "        \"val1\": \"Y\",\n" +
                "        \"val2\": \"Y\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"kpi\": {\n" +
                "    \"map-key-kpi1\": {\n" +
                "      \"code\": \"\"\n" +
                "    },\n" +
                "    \"map-key-kpi2\": {\n" +
                "      \"code\": \"\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"dim\": {\n" +
                "    \"dimBindingMap\": {\n" +
                "      \"user_id\": {\n" +
                "        \"code\": \"user_id\",\n" +
                "        \"group\": \"sqla\",\n" +
                "        \"caseWhenBinding\": {\n" +
                "          \"elseVal\": \"NA\",\n" +
                "          \"whenValList\": {\n" +
                "            \"whenValFieldList\": [\n" +
                "              {\n" +
                "                \"code\": \"LP_FLAG\",\n" +
                "                \"symbol\": \">\",\n" +
                "                \"val\": \"0\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"code\": \"CP_FLAG\",\n" +
                "                \"symbol\": \">\",\n" +
                "                \"val\": \"0\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"thenVal\": null,\n" +
                "            \"childCaseWhenBinding\": {\n" +
                "              \"code\": null,\n" +
                "              \"whenValList\": {\n" +
                "                \"whenValFieldList\": [\n" +
                "                  {\n" +
                "                    \"code\": \"CP_SALES_EXCL_VAT\",\n" +
                "                    \"symbol\": \">\",\n" +
                "                    \"val\": \"LP_SALES_EXCL_VAT\"\n" +
                "                  }\n" +
                "                ],\n" +
                "                \"thenVal\": \"BRAND INCREAS\",\n" +
                "                \"childCaseWhenBinding\": null\n" +
                "              },\n" +
                "              \"elseVal\": \"BRAND DECREASE\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"order_id\": {\n" +
                "        \"code\": \"order_id\",\n" +
                "        \"group\": \"sqla\",\n" +
                "        \"caseWhenBinding\": null\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        BindingInfo bindingInfo = JSON.parseObject(json, BindingInfo.class);

        String context = "select\n" +
                "   #{dimCol(\"order_id\",\"订单id\",\"t1.order_id\",\"sqla\")},\n" +
                "   #{dimCol(\"user_id\",\"用户id\",\"t1.user_id\",\"sqla\")},\n" +
                "   #{kpiCol(\"kpi1\",\"消费总金额\",\"sum(t1.amount)\")}" +
                "   \n" +
                "from test t1 #{groupBy(\"sqla\")}";
        String result = BeetlSqlParseUtils.renderWithBinding(context, bindingInfo);
        System.out.println("----------------");
        System.out.println(result);
    }
}
