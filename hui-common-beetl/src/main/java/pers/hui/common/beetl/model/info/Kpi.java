package pers.hui.common.beetl.model.info;

import lombok.Data;
import pers.hui.common.beetl.model.SqlBinding;

import java.util.Map;

/**
 * <code>Kpi</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/21 0:28.
 *
 * @author Ken.Hu
 */
@Data
public class Kpi {
    private Map<String, SqlBinding> kpiBindingMap;
}
