package pers.hui.common.beetl.fun;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <code>DynamicRouteFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 11:31.
 *
 * @author Gary.Hu
 */
@Deprecated
public class DynamicRouteFun implements Function {
    /**
     * 动态路由实现
     * 形式：
     * <%
     * var output1 = dim1.out or dim2.out or dim3.out;
     * var output2 = dim2.out or dim3.out;
     * var output3 = dim3.out;
     * var test1 = ('code','comment','val',${output1});
     * var test2 = ('code','comment','val',${output2});
     * var test3 = ('code','comment','val',${output3});
     * %>
     * #{test1}
     * #{test2}
     * #{test3}
     *
     *
     * @param params 入参信息
     * @param context 上下文
     * @return
     */
    @Override
    public Object call(Object[] params, Context context) {
        for (Object param : params) {
            System.out.println(param);
        }
        String collect = Arrays.stream(params).map(String::valueOf).collect(Collectors.joining("+"));
        System.out.println(collect);
        return collect;
    }
}
