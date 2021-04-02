package pers.hui.common.beetl.binding;

import lombok.Data;

/**
 * <code>IncludeBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/3 0:33.
 *
 * @author Ken.Hu
 */
@Data
public class IncludeBinding implements BindingInfo{
    private String code;
    private String includeContent;
}
