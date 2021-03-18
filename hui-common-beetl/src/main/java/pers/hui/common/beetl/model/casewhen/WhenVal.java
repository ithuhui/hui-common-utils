package pers.hui.common.beetl.model.casewhen;

import lombok.Data;

import java.util.List;

/**
 * <code>WhenVal</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/18 22:25.
 *
 * @author Ken.Hu
 */
@Data
public class WhenVal {
    /**
     * case when col1 > 10 and col < 20
     */
    private List<WhenValField> whenValFieldList;

    private String thenVal;

    private CaseWhenBinding childCaseWhenBinding ;
}
