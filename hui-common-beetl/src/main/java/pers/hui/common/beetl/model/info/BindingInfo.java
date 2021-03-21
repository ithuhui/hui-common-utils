package pers.hui.common.beetl.model.info;

import lombok.Data;

/**
 * <code>BindingInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/20 23:48.
 *
 * @author Ken.Hu
 */
@Data
public class BindingInfo {

    private Include include;
    private Dim dim;
    private Kpi kpi;
    private Where where;


}
