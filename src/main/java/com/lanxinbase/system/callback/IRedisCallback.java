package com.lanxinbase.system.callback;

import redis.clients.jedis.Jedis;

/**
 * Created by alan.luo on 2017/8/4.
 */
public interface IRedisCallback {
    Object doWithRedis(Jedis jedis);
}
