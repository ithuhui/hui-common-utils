package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.ParseCons;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.utils.ParseUtils;

/**
 * <code>WhereDefine</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/24 17:27.
 *
 * @author Gary.Hu
 */
public class WhereDefineFun implements Function {
    /**
     * 定义where可选的变量
     * #{whereDefine("sqlb","t1_test","t1_test2","t1_test3")}
     *
     * @param params  入参数组
     * @param context 上下文
     * @return 空
     */
    @Override
    public Object call(Object[] params, Context context) {
        SqlContext sqlContext = SqlContext.instance(context);
        String group = String.valueOf(params[0]);
        Object[] whereArgs = new String[params.length - 1];
        System.arraycopy(params, 1, whereArgs, 0, params.length - 1);
        for (Object whereField : whereArgs) {
            String whereFieldStr = String.valueOf(whereField);
            FunVal funVal = FunVal.builder()
                    .group(group)
                    .key(ParseUtils.keyGen(FunType.WHERE_DEFINE, whereFieldStr))
                    .code(whereFieldStr)
                    .val(whereFieldStr)
                    .funType(FunType.WHERE_DEFINE)
                    .build();
            sqlContext.addFunVal(FunType.WHERE_DEFINE, funVal);
        }
        return ParseCons.EMPTY_STR;
    }
}
