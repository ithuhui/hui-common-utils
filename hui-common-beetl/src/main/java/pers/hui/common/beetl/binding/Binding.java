package pers.hui.common.beetl.binding;

import lombok.Data;
import pers.hui.common.beetl.FunType;

/**
 * <code>BindingInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 22:59.
 *
 * @author Ken.Hu
 */
@Data
public abstract class Binding {
    private FunType funType;
}
