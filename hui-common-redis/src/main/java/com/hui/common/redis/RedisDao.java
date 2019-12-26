package com.hui.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <code>RedisDao</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/21 16:19.
 *
 * @author Gary.Hu
 */
public enum RedisDao {

    /**
     * init single
     */
    INSTANCE;

    private static final int REDIS_MAX_TOTAL = 100;
    private static final int REDIS_MAX_IDLE = 100;
    private static final int REDIS_MAX_WAIT = 100;

    private static final int CONNECT_TIME_OUT = 10000;

    private String host;
    private int port;
    private String password;

    private JedisPool jedisPool;

    private RedisDao init(String host, int port, String password) {
        return this;
    }

    private RedisDao init(String host, int port) {
        return this;
    }

    private void initJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(REDIS_MAX_TOTAL);
        jedisPoolConfig.setMaxIdle(REDIS_MAX_IDLE);
        jedisPoolConfig.setMaxWaitMillis(REDIS_MAX_WAIT);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);
        if (null != password) {
            this.jedisPool = new JedisPool(jedisPoolConfig, host, port, CONNECT_TIME_OUT, password);
        } else {
            this.jedisPool = new JedisPool(jedisPoolConfig, host, port, CONNECT_TIME_OUT);
        }
    }


    public HashFun hashFun() {
        return new HashFun();
    }

    public ListFun listFun() {
        return new ListFun();
    }

    public SetFun setFun() {
        return new SetFun();
    }

    public String set(String key, String val) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.set(key, val);
        return res;
    }

    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.get(key);
        return res;
    }

    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.del(key);
        return res;
    }

    public Long del(String... keys) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.del(keys);
        return res;
    }

    public Boolean exist(String key) {
        Jedis jedis = jedisPool.getResource();
        Boolean res = jedis.exists(key);
        return res;
    }

    public Long exist(String... keys) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.exists(keys);
        return res;
    }

    public Long expire(String key, int expireTime) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.expire(key, expireTime);
        return res;
    }

    public Long expireAt(String key, int expireTime) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.expireAt(key, expireTime);
        return res;
    }

    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.ttl(key);
        return res;
    }

    public String getSet(String key, String val) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.getSet(key, val);
        return res;
    }

}
