package pers.hui.common.beetl.fun;

import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.utils.ParseUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>WhereDefine</code>
 * <desc>
 * 描述：定义where可选的变量
 * #{whereDefine("sqlb","t1_test","t1_test2","t1_test3")}
 * <desc/>
 * <b>Creation Time:</b> 2021/3/24 17:27.
 *
 * @author Gary.Hu
 */
public class WhereDefineFun extends BaseSqlParseFun {
    @Override
    FunType defineFunType() {
        return FunType.WHERE_DEFINE;
    }

    @Override
    String parse(List<FunVal> funVals, SqlContext sqlContext) {
        return ParseCons.EMPTY_STR;
    }


    @Override
    List<FunVal> genFunVals(Object[] params) {
        String group = String.valueOf(params[0]);
        Object[] whereArgs = new String[params.length - 1];
        System.arraycopy(params, 1, whereArgs, 0, params.length - 1);
        return Arrays.stream(whereArgs).map(whereField -> {
            String whereFieldStr = String.valueOf(whereField);
            return FunVal.builder()
                    .group(group)
                    .key(ParseUtils.keyGen(FunType.WHERE_DEFINE, whereFieldStr))
                    .code(whereFieldStr)
                    .val(whereFieldStr)
                    .funType(FunType.WHERE_DEFINE)
                    .build();
        }).collect(Collectors.toList());
    }
}
