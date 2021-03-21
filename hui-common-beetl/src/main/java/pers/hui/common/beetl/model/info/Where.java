package pers.hui.common.beetl.model.info;

import lombok.Data;
import pers.hui.common.beetl.model.WhereBinding;

import java.util.List;

/**
 * <code>Where</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/21 0:28.
 *
 * @author Ken.Hu
 */
@Data
public class Where {
    private String expression;
    private String group;
    private List<WhereBinding> whereBindings;
}
