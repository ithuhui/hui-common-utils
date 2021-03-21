package pers.hui.common.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import pers.hui.common.beetl.fun.*;
import pers.hui.common.beetl.model.info.GroupBy;
import pers.hui.common.beetl.model.SqlKey;
import pers.hui.common.beetl.model.info.BindingInfo;

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
public class BeetlSqlParseUtils {

    /**
     * 初始化模板工具类
     *
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
        groupTemplate.registerFunction("where", new WhereFun());
        groupTemplate.registerFunction("test", new DynamicRouteFun());
        groupTemplate.registerFunction("groupBy", new GroupByFun());
        groupTemplate.registerFunction("includeSub", new IncludeFun());
        return groupTemplate;
    }

    /**
     * 通过文本获取字段值信息
     *
     * @param content
     * @throws IOException
     */
    public static Map<String, Object> getFieldValue(String content) throws IOException {
        Template template = groupTemplateInit().getTemplate(content);
        template.render();
        Context ctx = template.getCtx();
        return ctx.globalVar;
    }

    public static String renderWithBinding(String content, BindingInfo bindingInfo) throws IOException {
        GroupTemplate groupTemplate = groupTemplateInit();

        Template template = groupTemplate.getTemplate(content);

        template.getCtx().set(SqlKey.INCLUDE.name(),bindingInfo.getInclude());
        template.getCtx().set(SqlKey.KPI.name(),bindingInfo.getKpi());
        template.getCtx().set(SqlKey.DIM.name(),bindingInfo.getDim());
        template.getCtx().set(SqlKey.WHERE.name(),bindingInfo.getWhere());
        template.getCtx().set(SqlKey.GROUP_BY.name(),new GroupBy(bindingInfo.getDim()));

        return template.render();
    }

}
