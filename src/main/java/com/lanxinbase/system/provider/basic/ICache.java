package com.lanxinbase.system.provider.basic;

/**
 * Created by alan.luo on 2017/8/4.
 */
public interface ICache {

    /**
     * get a cache.
     * @param key
     * @return
     */
    Object get(String key) throws Exception;


    /**
     * put a cache condition by timeout
     * @param key
     * @param val
     * @param millis
     * @return
     */
    boolean put(String key, Object val, long millis);

    /**
     * is has cache by key.
     * @param key
     * @return
     */
    boolean has(String key);

    /**
     * delete a cache.
     * @param key
     */
    boolean remove(String key);

    /**
     * get cache count.
     * @return
     */
    int count();

    /**
     * clear all cache.
     */
    void clear();

    Object getAll();

    void checkAll();
}
