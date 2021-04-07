package pers.hui.common.beetl;

import pers.hui.common.beetl.binding.*;
import pers.hui.common.beetl.utils.ParseUtils;

/**
 * <code>SqlKey</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/21 0:36.
 *
 * @author Ken.Hu
 */
public enum FunType {

    /**
     * Include基础模板/变量模板
     */
    INCLUDE_BASE{
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            IncludeBinding binding = (IncludeBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getCode());
        }
    },
    INCLUDE_GLOBAL_VAL {
        @Override
        String genKeyByBinding(Binding binding) {
            return null;
        }
    },
    DIM {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            DimBinding binding = (DimBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup(), binding.getCode());
        }
    },
    KPI {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            KpiBinding binding = (KpiBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup(),binding.getCode());
        }
    },
    WHERE {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            WhereBinding binding = (WhereBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup());
        }
    },
    WHERE_DEFINE {
        @Override
        String genKeyByBinding(Binding binding) {
            return null;
        }
    },
    GROUP_BY {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            GroupByBinding binding = (GroupByBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup());
        }
    },
    HAVING{
        @Override
        String genKeyByBinding(Binding binding) {
            return null;
        }
    }
    ;

    abstract String genKeyByBinding(Binding binding);

    @Override
    public String toString() {
        return this.name();
    }
}
