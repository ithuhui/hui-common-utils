package pers.hui.common.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import pers.hui.common.beetl.fun.WhereFun;

import java.io.IOException;

/**
 * <code>BeetlCore</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/16 23:01.
 *
 * @author Ken.Hu
 */
public class BeetlCore {
    public static GroupTemplate groupTemplateInit() throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        return new GroupTemplate(resourceLoader, cfg);
    }


    public static void main(String[] args) throws IOException {
        String templateStr = "";
        GroupTemplate groupTemplate = groupTemplateInit();
        groupTemplate.registerFunction("where", new WhereFun());
        //获取模板
        Template template = groupTemplate.getTemplate("hello,${where(\"groupCode1\", \"groupName\", \"txn.member,成员\", \"order，订单\", \"phone,手机\")}");
        template.binding("name", "beetl");
        //渲染结果
        String result = template.render();
        System.out.println(result);
    }
}
