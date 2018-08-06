package com.lanxinbase.system.provider.handler;

import com.lanxinbase.constant.ConstantRedis;
import com.lanxinbase.system.callback.IRedisCallback;
import com.lanxinbase.system.provider.basic.DCacheData;
import com.lanxinbase.system.provider.basic.ICache;
import com.lanxinbase.system.utils.SerializeUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by alan.luo on 2017/8/4.
 * <p>
 * CacheRedis redis = new CacheRedis("testKey");
 * redis.put("redisTest","redisTest1",10);
 * redis.put("redisTest1","redisTest1",0);
 * redis.remove("redisTest2");
 * redis.put("redisUserList",Object,50);
 * <p>
 * redis.count()
 * redis.get("redisTest")
 * redis.get("redisTest2")
 * redis.get("redisUserList")
 */
public class CacheRedisHandler implements ICache {

    protected JedisPool jedisPool = null;
    protected String id = null;

    public CacheRedisHandler(String id) {
        jedisPool = new JedisPool(ConstantRedis.IP, ConstantRedis.PORT);


//        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
//        config.setMaxIdle(ConstantRedis.maxIdle);
//        config.setMaxTotal(ConstantRedis.maxTotal);
//        config.setMaxWaitMillis(ConstantRedis.maxWaitMillis);
//        config.setMinIdle(8);
//        config.setTestOnBorrow(ConstantRedis.testOnBorrow);
//        jedisPool = new JedisPool(config,ConstantRedis.IP,ConstantRedis.PORT,2000,ConstantRedis.PASSWORD);

        this.id = id;
    }

    public Object execute(IRedisCallback callback) {
        Jedis jedis = jedisPool.getResource();
        try {
            return callback.doWithRedis(jedis);
        } finally {
            jedis.close();
        }
    }

    public String getId() {
        return id;
    }

    public Object get(final String key) {
        return execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                if (jedis.hexists(getId().getBytes(), key.getBytes())) {
                    try {
                        DCacheData val = (DCacheData) SerializeUtils.unSerialize(jedis.hget(id.getBytes(), key.getBytes()));
                        if (val.checkExpired()) {
                            return val.getVal();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    jedis.hdel(getId().getBytes(), key.getBytes());
                }
                return null;
            }
        });
    }

    @Override
    public boolean put(final String key, Object val, long millis) {
        if (millis <= 0) {
            millis = 86400;
        }
        millis = millis * 1000;

        millis = System.currentTimeMillis() + millis;
        final DCacheData data = new DCacheData(val, millis);

        return (boolean) execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                long in = jedis.hset(getId().getBytes(), key.getBytes(), SerializeUtils.serialize(data));
                return in == 1 ? true : false;
            }
        });
    }

    @Override
    public boolean has(final String key) {
        return (boolean) execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                return jedis.hexists(getId().getBytes(), key.getBytes());
            }
        });
    }

    @Override
    public boolean remove(final String key) {
        return (boolean) execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                long in = jedis.hdel(getId().getBytes(), key.getBytes());
                return in == 1 ? true : false;
            }
        });

    }

    @Override
    public int count() {
        return (int) execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                return jedis.hgetAll(getId().getBytes()).size();
            }
        });
    }

    @Override
    public void clear() {
        execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                jedis.del(getId().getBytes());
                return null;
            }
        });
    }

    /**
     * 获取所有的缓存数据
     * @return Map<String,Object>对象
     */
    @Override
    public Object getAll() {
        final Map<String,Object> data = new HashMap<>();
        return execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                Set<byte[]> keys = jedis.hkeys(getId().getBytes());
                for (byte[] b : keys) {
                    DCacheData val = (DCacheData) SerializeUtils.unSerialize(jedis.hget(getId().getBytes(), b));
                    if (val == null || !val.checkExpired()) {
                        jedis.hdel(getId().getBytes(),b);
                    }else {
                        data.put(new String(b),val.getVal());
                    }
                }
                return data;
            }
        });
    }

    /**
     * 清除无用的缓存
     */
    @Override
    public void checkAll() {
        execute(new IRedisCallback() {
            @Override
            public Object doWithRedis(Jedis jedis) {
                Set<byte[]> keys = jedis.hkeys(getId().getBytes());
                for (byte[] b : keys) {
                    DCacheData val = (DCacheData) SerializeUtils.unSerialize(jedis.hget(getId().getBytes(), b));
                    if (!val.checkExpired()) {
                        jedis.hdel(getId().getBytes(),b);
                    }
                }
                return null;
            }
        });
    }
}
