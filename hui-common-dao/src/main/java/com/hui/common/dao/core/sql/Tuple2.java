package com.hui.common.dao.core.sql;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * <code>Tuple2</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/8 23:59.
 *
 * @author Gary.Hu
 */
@ToString
@AllArgsConstructor
public class Tuple2<T1, T2> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T1 t1;
    private T2 t2;

    public T1 _1() {
        return t1;
    }

    public T2 _2() {
        return t2;
    }

}
