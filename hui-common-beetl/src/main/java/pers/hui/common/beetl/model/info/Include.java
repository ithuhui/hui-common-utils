package pers.hui.common.beetl.model.info;

import lombok.Data;
import pers.hui.common.beetl.model.IncludeBinding;

import java.util.Map;

/**
 * <code>Include</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/21 0:28.
 *
 * @author Ken.Hu
 */
@Data
public class Include {
    private Map<String, IncludeBinding> baseTemplate;
    private Map<String, IncludeBinding> globalTemplate;
}
