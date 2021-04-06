package pers.hui.common.beetl.binding;

import lombok.Data;

import java.util.List;

/**
 * <code>WhereBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/6 22:39.
 *
 * @author Ken.Hu
 */
@Data
public class WhereBinding implements BindingInfo {
    private String group;
    private String expression;
    private List<WhereInfo> whereInfos;

    @Data
    public static class WhereInfo {
        private String code;
        private String val;
        private String id;
        private String operator;
        private String operatorVal;
    }
}
