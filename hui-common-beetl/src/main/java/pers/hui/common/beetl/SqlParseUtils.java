package pers.hui.common.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.StringTemplateResourceLoader;
import pers.hui.common.beetl.fun.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 去除多余逗号
     */
    public static final String SYMBOL_REGEX = "[\\s]*[,][\\s]*[,]{1,}([\\s]+|[,]+)*";
    /**
     * 去除from前逗号
     */
    public static final String SYMBOL_REGEX2 = "(?i)(,)(\\s)*(from)";
    /**
     * 去除select前逗号
     */
    public static final String SYMBOL_REGEX3 = "(?i)(select)(\\s)*(,)";


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

    public static Map<FunType, SqlParseInfo> getFunValMap(String content) throws IOException {
        String contextNew = extractTextByRegex(content);
        GroupTemplate groupTemplate = groupTemplateInit();
        groupTemplate.getSharedVars().put(ParseCons.INFO,new HashMap<FunType, SqlParseInfo>(FunType.values().length));

        System.out.println(contextNew);
        Template template = groupTemplate.getTemplate(contextNew);
        Context ctx = template.getCtx();
        template.render();

        return SqlContext.instance(ctx).getInfo();
    }

    /**
     * 模板引擎校验模板写法是否有异常
     * @param content
     * @return
     * @throws IOException
     */
    public static boolean validateContent(String content) throws IOException {
        GroupTemplate groupTemplate = groupTemplateInit();
        BeetlException beetlException = groupTemplate.validateTemplate(content);
        return null == beetlException;
    }



    public static String perfectParseContent(String content) {
        return content.replaceAll(SYMBOL_REGEX, ",")
                .replaceAll(SYMBOL_REGEX2, " from")
                .replaceAll(SYMBOL_REGEX3, "select ");
    }


    public static String extractTextByRegex(String text) {
        String regex = "#\\{.*}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            if (matcher.group().contains("(")) {
                result.append(matcher.group()).append("\n");
            }

        }
        return result.toString();
    }

}
