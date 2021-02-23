package pers.hui.common.redis.core;

import redis.clients.jedis.Jedis;

/**
 * <code>ListFun</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/22 23:35.
 *
 * @author Gary.Hu
 */
public class ListFun {

    private Jedis jedis;

    public String leftPop(String key) {
        String res = jedis.lpop(key);
        return res;
    }
}
