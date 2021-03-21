package pers.hui.common.beetl.model.info;

import lombok.Data;
import pers.hui.common.beetl.model.DimBinding;

import java.util.Map;

/**
 * <code>Dim</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/21 0:28.
 *
 * @author Ken.Hu
 */
@Data
public class Dim {
    private Map<String, DimBinding> dimBindingMap;
}
