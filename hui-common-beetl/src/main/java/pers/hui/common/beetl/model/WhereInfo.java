package pers.hui.common.beetl.model;

import lombok.Data;

import java.util.List;

/**
 * <code>WhereInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/19 0:16.
 *
 * @author Ken.Hu
 */
@Data
public class WhereInfo {
    private String expression;
    private String group;
    private List<WhereBinding> whereBindings;
}
