package pers.hui.common.dao;

import pers.hui.common.dao.core.BaseDao;
import pers.hui.common.dao.core.BaseMapper;

/**
 * <code>TestBaseMapper</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/1/3 14:30.
 *
 * @author Gary.Hu
 */
public class TestBaseMapper<U, I extends Number> extends BaseMapper<User,Integer> {
    public TestBaseMapper(BaseDao baseDao, Class clazz) {
        super(baseDao, clazz);
    }
}
