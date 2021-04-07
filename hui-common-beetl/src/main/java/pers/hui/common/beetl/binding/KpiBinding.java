package pers.hui.common.beetl.binding;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <code>KpiBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/3 0:35.
 *
 * @author Ken.Hu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KpiBinding extends Binding {
    private String code;
    private String group;
    private String expression;
}
