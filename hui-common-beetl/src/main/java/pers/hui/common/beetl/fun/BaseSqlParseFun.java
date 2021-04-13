package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.SqlContext;
import pers.hui.common.beetl.binding.Binding;

import java.util.List;

/**
 * <code>BaseFunHandler</code>
 * <desc>
 * 描述：模板
 * <desc/>
 * <b>Creation Time:</b> 2021/4/13 11:24.
 *
 * @author Gary.Hu
 */
public abstract class BaseSqlParseFun<T extends Binding> implements Function {
    private Object[] params;
    private SqlContext<T> sqlContext;
    private FunType funType;

    public void init(SqlContext<T> sqlContext, Object[] params, FunType funType) {
        this.sqlContext = sqlContext;
        this.params = params;
        this.funType = funType;
    }

    /**
     * 定义方法类型
     *
     * @return funType
     */
    abstract FunType defineFunType();

    /**
     * 解析转换
     *
     * @param funVals
     * @param sqlContext
     * @return
     */
    abstract String parse(List<FunVal> funVals, SqlContext<T> sqlContext);

    /**
     * 构建方法元信息
     *
     * @param params 方法入参
     * @return 方法元信息集合
     */
    abstract List<FunVal> genFunVals(Object[] params);

    public void saveFunVals(List<FunVal> funVals) {
        if (null != funVals && !funVals.isEmpty()) {
            funVals.forEach(funVal -> this.sqlContext.addFunVal(funType, funVal));
        }
    }

    @Override
    public Object call(Object[] params, Context context) {
        SqlContext<T> sqlContext = SqlContext.instance(context);
        init(sqlContext, params, defineFunType());
        List<FunVal> funVals = genFunVals(params);
        saveFunVals(funVals);
        return parse(funVals, sqlContext);
    }
}
