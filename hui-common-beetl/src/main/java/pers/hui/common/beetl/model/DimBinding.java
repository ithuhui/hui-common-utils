package pers.hui.common.beetl.model;

import lombok.Data;

/**
 * <code>DimBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/20 23:56.
 *
 * @author Ken.Hu
 */
@Data
public class DimBinding extends SqlBinding{
    private String group;
    private CaseWhenBinding caseWhenBinding;
}
