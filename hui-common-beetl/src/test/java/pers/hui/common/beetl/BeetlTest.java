package pers.hui.common.beetl;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.junit.Test;
import pers.hui.common.beetl.fun.DimColFun;
import pers.hui.common.beetl.fun.KpiColFun;

import java.io.IOException;

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
                "    #{dimCol(\"groupA\", \"t1.grp\", \"test1\")}\n" +
                "    #{kpiCol(\"groupB\", \"t1.grp\", \"test2\")}\n" +
                "from test";
        GroupTemplate groupTemplate = BeetlCore.groupTemplateInit();
        groupTemplate.registerFunction("dimCol", new DimColFun());
        groupTemplate.registerFunction("kpiCol", new KpiColFun());
        //获取模板
        Template template = groupTemplate.getTemplate(templateStr);
        template.binding("name", "beetl");
        //渲染结果
        String result = template.render();
        System.out.println(result);
    }
}
