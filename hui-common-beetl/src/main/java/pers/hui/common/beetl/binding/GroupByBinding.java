package pers.hui.common.beetl.binding;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * <code>GroupByBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/3 0:23.
 *
 * @author Ken.Hu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupByBinding extends Binding {
    private String group;
    private Set<String> codes;

}
