package pers.hui.common.beetl.fun;

import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.HavingBinding;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>HavingDefine</code>
 * <desc>
 * 描述：#{havingDefine("GROUP","HAVING_COL1","HAVING_COL2")}
 * <desc/>
 * <b>Creation Time:</b> 2021/4/13 17:25.
 *
 * @author Gary.Hu
 */
public class HavingDefineFun extends BaseSqlParseFun<HavingBinding> {
    @Override
    FunType defineFunType() {
        return FunType.HAVING_DEFINE;
    }

    @Override
    String parse(List<FunVal> funVals, SqlContext<HavingBinding> sqlContext) {
        return ParseCons.EMPTY_STR;
    }

    @Override
    List<FunVal> genFunVals(Object[] params) {
        String group = String.valueOf(params[0]);
        Object[] havingArgs = new String[params.length - 1];
        System.arraycopy(params, 1, havingArgs, 0, params.length - 1);
        return Arrays.stream(havingArgs).map(whereField -> {
            String whereFieldStr = String.valueOf(whereField);
            return FunVal.builder()
                    .group(group)
                    .key(ParseUtils.keyGen(FunType.HAVING_DEFINE, whereFieldStr))
                    .code(whereFieldStr)
                    .val(whereFieldStr)
                    .funType(FunType.HAVING_DEFINE)
                    .build();
        }).collect(Collectors.toList());
    }
}
