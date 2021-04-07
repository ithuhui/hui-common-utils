package pers.hui.common.beetl;

import lombok.Data;
import pers.hui.common.beetl.binding.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>BindingInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/7 16:49.
 *
 * @author Gary.Hu
 */
@Data
public class BindingInfo {
    private List<DimBinding> dimBindingInfos = new ArrayList<>();
    private List<IncludeBinding> includeBaseBindings = new ArrayList<>();
    private List<IncludeBinding> includeGlobalValBindings = new ArrayList<>();
    private List<KpiBinding> kpiBindings = new ArrayList<>();
    private List<WhereBinding> whereBindings = new ArrayList<>();
}
