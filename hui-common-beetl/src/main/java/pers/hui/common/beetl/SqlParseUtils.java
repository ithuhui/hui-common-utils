package pers.hui.common.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.StringTemplateResourceLoader;
import pers.hui.common.beetl.fun.*;

import java.io.IOException;

/**
 * <code>SqlParseUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 22:52.
 *
 * @author Ken.Hu
 */
public class SqlParseUtils {
    /**
     * 初始化模板工具类
     *
     * @return 解析模板类
     * @throws IOException
     */
    public static GroupTemplate groupTemplateInit() throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        cfg.setPlaceholderStart("#{");
        cfg.setPlaceholderEnd("}");
        GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, cfg);
        groupTemplate.registerFunction("dim", new DimColFun());
        groupTemplate.registerFunction("kpi", new KpiColFun());
        groupTemplate.registerFunction("where", new WhereFun());
        groupTemplate.registerFunction("groupBy", new GroupByFun());
        groupTemplate.registerFunction("include", new IncludeFun());
        groupTemplate.registerFunction("whereDefine", new WhereDefineFun());
        return groupTemplate;
    }
}
