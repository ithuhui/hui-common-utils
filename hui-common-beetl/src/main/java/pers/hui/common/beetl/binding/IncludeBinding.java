package pers.hui.common.beetl.binding;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
public class IncludeBinding extends Binding {
    private String code;
    private String includeContent;


}
