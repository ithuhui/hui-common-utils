package pers.hui.common.beetl.model;

import lombok.Data;

import java.util.List;

/**
 * <code>WhereBinding</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/18 23:20.
 *
 * @author Ken.Hu
 */
@Data
public class WhereBinding extends ValBinding{
    private String order;
    private String symbol;
    private String val1;
    private String val2;
}
