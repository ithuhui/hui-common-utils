package pers.hui.common.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import pers.hui.common.beetl.fun.DimColFun;
import pers.hui.common.beetl.fun.KpiColFun;

import java.io.IOException;
import java.util.Map;

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

    /**
     * 初始化模板工具类
     * @return
     * @throws IOException
     */
    public static GroupTemplate groupTemplateInit() throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        cfg.setPlaceholderStart("#{");
        cfg.setPlaceholderEnd("}");
        GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, cfg);
        groupTemplate.registerFunction("dimCol", new DimColFun());
        groupTemplate.registerFunction("kpiCol", new KpiColFun());
        return groupTemplate;
    }

    /**
     * 通过文本获取字段值信息
     * @param content
     * @throws IOException
     */
    public static void getFieldValue(String content) throws IOException {
        Template template = groupTemplateInit().getTemplate(content);
        Context ctx = template.getCtx();
        Map<String, Object> globalVar = ctx.globalVar;
    }
}
