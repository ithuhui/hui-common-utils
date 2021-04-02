package pers.hui.common.beetl.binding;

import lombok.Data;

import java.util.List;

/**
 * <code>DimBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 23:59.
 *
 * @author Ken.Hu
 */
@Data
public class DimBinding implements BindingInfo {
    private String code;
    private String group;
    private CaseWhenBinding caseWhenBinding;

    @Data
    public static class CaseWhenBinding {
        private String alise;
        private List<WhenVal> whenValList;
        private String elseVal;
    }
    @Data
    public static class WhenValField {
        private String code;
        private String symbol;
        private String val;
    }

    @Data
    public static class WhenVal {
        private List<WhenValField> whenValFieldList;

        private String thenVal;

        private CaseWhenBinding childCaseWhenBinding ;
    }

}
