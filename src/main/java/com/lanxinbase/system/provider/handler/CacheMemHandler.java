package com.lanxinbase.system.provider.handler;

import com.lanxinbase.system.provider.basic.DCacheData;
import com.lanxinbase.system.provider.basic.ICache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by alan.luo on 2017/8/4.
 */
public class CacheMemHandler  implements ICache {

    protected ConcurrentMap<String,DCacheData> cacheMap = null;

    public CacheMemHandler(){
        this.cacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public Object get(String key){
        if (has(key)){
            DCacheData val = cacheMap.get(key);
            if (val.checkExpired()){
                return val.getVal();
            }
            cacheMap.remove(key);
        }
        return null;
    }

    /**
     * put a cache.
     * @param key
     * @param val
     * @param millis the unit is second.if the param <= 0,when it = 86400000.
     * @return
     */
    @Override
    public boolean put(String key, Object val, long millis) {
        if (millis <= 0){
            millis = 86400;
        }
        millis = millis * 1000;

        millis = System.currentTimeMillis() + millis;
        DCacheData data = new DCacheData(val,millis);
        cacheMap.put(key,data);
        return true;
    }

    @Override
    public boolean has(String key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public boolean remove(String key) {
        cacheMap.remove(key);
        return true;
    }

    @Override
    public int count() {
        return cacheMap.size();
    }

    @Override
    public void clear() {
        cacheMap.clear();
    }

    @Override
    public Object getAll() {
        return null;
    }

    @Override
    public void checkAll() {

    }

    public ConcurrentMap<String, DCacheData> getCacheMap() {
        return cacheMap;
    }

}
