package pers.hui.common.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.StringTemplateResourceLoader;
import pers.hui.common.beetl.binding.Binding;
import pers.hui.common.beetl.fun.*;
import pers.hui.common.beetl.utils.ParseUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    /**
     * 获取sql解析情况
     *
     * @param content 解析文本
     * @return
     * @throws IOException
     */
    public static List<Map<String, FunVal>> getFunValMap(String content) throws IOException {
        String contextNew = extractTextByRegex(content);
        GroupTemplate groupTemplate = groupTemplateInit();

        Template template = groupTemplate.getTemplate(contextNew);
        Context ctx = template.getCtx();
        template.render();
        Map<FunType, SqlParseInfo<Binding>> info = SqlContext.instance(ctx).getInfo();
        return info.values().stream().map(SqlParseInfo::getParseFunValMap).collect(Collectors.toList());
    }


    /**
     * 模板引擎校验模板写法是否有异常
     *
     * @param content
     * @return
     * @throws IOException
     */
    public static boolean validateContent(String content) throws IOException {
        GroupTemplate groupTemplate = groupTemplateInit();
        BeetlException beetlException = groupTemplate.validateTemplate(content);
        return null == beetlException;
    }

    /**
     * 动态路由的绑定
     *
     * @param template       模板
     * @param parseFunValMap 函数元信息
     * @param dimBindingMap  DIM绑定信息
     */
    private static void remarkDynamicRoute(Template template, List<Map<String, FunVal>> parseFunValMap, Map<String, List<Binding>> dimBindingMap) {
        parseFunValMap.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .forEach(val -> {
                    boolean isOutput = false;
                    if (dimBindingMap.containsKey(val.getKey())) {
                        isOutput = true;
                    }
                    String outputVal = ParseUtils.genOutPutVal(val);
                    template.binding(outputVal, isOutput);
                });
    }


    public static String perfectParseContent(String content) {
        return content.replaceAll(SYMBOL_REGEX, ",")
                .replaceAll(SYMBOL_REGEX2, " from")
                .replaceAll(SYMBOL_REGEX3, "select ");
    }

    public static String renderWithBinding(String content, BindingInfo bindingInfo) throws IOException {
        List<Map<String, FunVal>> funValMap = getFunValMap(content);
        GroupTemplate groupTemplate = groupTemplateInit();
        Template template = groupTemplate.getTemplate(content);
        SqlContext sqlContext = SqlContext.instance(template.getCtx());
        // 数据绑定保存到全局变量
        dataBinding(sqlContext, bindingInfo);
        // 适配动态路由
        remarkDynamicRoute(template, funValMap, sqlContext.getBindingMap(FunType.DIM));
        return perfectParseContent(template.render());
    }

    private static void dataBinding(SqlContext sqlContext, BindingInfo bindingInfo) {
        bindingInfo.getBindings().forEach(binding -> {
            FunType funType = binding.getFunType();
            sqlContext.binding(funType, funType.genKeyByBinding(binding), binding);
        });
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
