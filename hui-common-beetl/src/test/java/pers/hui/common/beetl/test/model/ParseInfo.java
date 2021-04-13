package pers.hui.common.beetl.test.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>ParseInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/13 21:23.
 *
 * @author _Ken.Hu
 */
@Data
public class ParseInfo<T extends Bind> {
    private final Map<String, List<T>> bindingMap = new HashMap<>();
}
