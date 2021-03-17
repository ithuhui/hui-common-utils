package pers.hui.common.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.StringTemplateResourceLoader;

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
        cfg.setPlaceholderStart("#{");
        cfg.setPlaceholderEnd("}");
        return new GroupTemplate(resourceLoader, cfg);
    }
}
