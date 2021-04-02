package pers.hui.common.beetl;

import lombok.Builder;
import lombok.Data;

/**
 * <code>FunVal</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 22:27.
 *
 * @author Ken.Hu
 */
@Data
@Builder
public class FunVal {
    private String key;
    private String group;
    private String code;
    private String comment;
    private String val;
    private String parseVal;
    private FunType funType;
    private Object[] originVals;
}
