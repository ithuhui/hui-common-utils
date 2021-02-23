package pers.hui.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <code>Tuple3</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 0:06.
 *
 * @author Gary.Hu
 */
@ToString
@AllArgsConstructor
public class Tuple3<T1, T2, T3> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T1 t1;
    private T2 t2;
    private T3 t3;

    public T1 _1() {
        return t1;
    }

    public T2 _2() {
        return t2;
    }

    public T3 _3() {
        return t3;
    }
}
