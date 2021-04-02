package pers.hui.common.beetl.binding;

import lombok.Data;

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
@Data
public class GroupByBinding implements BindingInfo{
    private String group;
    private Set<String> codes;
}
