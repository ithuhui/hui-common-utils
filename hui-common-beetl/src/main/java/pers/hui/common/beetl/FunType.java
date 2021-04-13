package pers.hui.common.beetl;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor
@Getter
public enum FunType {

    /**
     * Include基础模板/变量模板
     */
    INCLUDE_BASE(IncludeBinding.class){
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            IncludeBinding binding = (IncludeBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getCode());
        }
    },
    INCLUDE_GLOBAL_VAL(IncludeBinding.class) {
        @Override
        String genKeyByBinding(Binding binding) {
            return null;
        }
    },
    DIM(DimBinding.class) {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            DimBinding binding = (DimBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup(), binding.getCode());
        }
    },
    KPI(KpiBinding.class) {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            KpiBinding binding = (KpiBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup(),binding.getCode());
        }
    },
    WHERE(WhereBinding.class) {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            WhereBinding binding = (WhereBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup());
        }
    },
    WHERE_DEFINE(Object.class) {
        @Override
        String genKeyByBinding(Binding binding) {
            return null;
        }
    },
    GROUP_BY(GroupByBinding.class) {
        @Override
        String genKeyByBinding(Binding bindingInfo) {
            GroupByBinding binding = (GroupByBinding) bindingInfo;
            return ParseUtils.keyGen(binding.getGroup());
        }
    },
    HAVING(HavingBinding.class){
        @Override
        String genKeyByBinding(Binding binding) {
            return null;
        }
    },
    HAVING_DEFINE(Object.class){
        @Override
        String genKeyByBinding(Binding binding) {
            return null;
        }
    },
    ;

    private Class clazz;

    abstract String genKeyByBinding(Binding binding);

    @Override
    public String toString() {
        return this.name();
    }
}
