package pers.hui.common.beetl;

import lombok.Data;
import pers.hui.common.beetl.binding.Binding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>ParseInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 22:47.
 *
 * @author Ken.Hu
 */
@Data
public class SqlParseInfo<T extends Binding> {
    private Map<String, FunVal> parseFunValMap = new HashMap<>();

    private Map<String, List<T>> bindingMap = new HashMap<>();
}
