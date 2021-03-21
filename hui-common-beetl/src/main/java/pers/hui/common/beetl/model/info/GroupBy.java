package pers.hui.common.beetl.model.info;

import lombok.Data;
import pers.hui.common.beetl.model.DimBinding;
import pers.hui.common.beetl.model.info.Dim;

import java.util.*;

/**
 * <code>GroupBy</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/21 0:39.
 *
 * @author Ken.Hu
 */
@Data
public class GroupBy {
    private final Map<String, Set<String>> groupBindingMap = new HashMap<>();

    public GroupBy(Dim dim) {
        init(dim);
    }

    private void init(Dim dim) {
        Map<String, DimBinding> dimBindingMap = dim.getDimBindingMap();
        for (Map.Entry<String, DimBinding> entry : dimBindingMap.entrySet()) {
            DimBinding dimBinding = entry.getValue();
            String group = dimBinding.getGroup();
            String code = dimBinding.getCode();
            groupBindingMap.putIfAbsent(group, new HashSet<>());
            Set<String> dimCodes = groupBindingMap.get(group);
            if (dimCodes != null){
                dimCodes.add(code);
            }
        }
    }
}
