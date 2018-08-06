package com.lanxinbase.system.provider;


import com.lanxinbase.constant.ConstantRedis;
import com.lanxinbase.system.basic.BasicProvider;
import com.lanxinbase.system.annotation.Provider;
import com.lanxinbase.system.provider.handler.CacheRedisHandler;

/**
 * Created by alan.luo on 2017/8/4.
 */
@Provider
public class CacheProvider extends BasicProvider {

//    protected CacheMemHandler cache;
    protected CacheRedisHandler cache;

    /**
     * default expired time is one hour.
     */
    public static final long EXPIRED_TIME = 3600;

    public CacheProvider(){
        super();
        cache = new CacheRedisHandler(ConstantRedis.cacheId);
    }

    public static CacheProvider getInstance(){
        return new CacheProvider();
    }

    public Object get(String key){
        return cache.get(key);
    }

    public void put(String key,Object val,long expired){
        if (expired <= 0){
            expired = EXPIRED_TIME ;
        }
        cache.put(key,val,expired);
    }

    public boolean has(String key){
        return cache.has(key);
    }

    public void remove(String key){
        cache.remove(key);
    }

    public void clear(){
        cache.clear();
    }

    public int count(){
       return cache.count();
    }

    /**
     * 获取所有的缓存数据
     * @return Map<String,Object>对象
     */
    public Object getAll(){
       return cache.getAll();
    }

    /**
     * 清除无用的缓存
     */
    public void checkAll(){
        cache.checkAll();
    }

}
