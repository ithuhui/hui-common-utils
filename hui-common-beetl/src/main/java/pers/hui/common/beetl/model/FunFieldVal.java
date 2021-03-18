package pers.hui.common.beetl.model;

import lombok.Builder;
import lombok.Data;

/**
 * <code>FunFieldVal</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/18 10:27.
 *
 * @author Gary.Hu
 */
@Data
@Builder
public class FunFieldVal {
    private String code;
    private String comment;
    private String value;
    private boolean isNested;
    private FunFieldVal nestedFunFieldVal;
}
