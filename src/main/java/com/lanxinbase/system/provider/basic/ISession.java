package com.lanxinbase.system.provider.basic;

import com.lanxinbase.system.exception.IllegalServiceException;

/**
 * Created by alan.luo on 2017/9/26.
 */
public interface ISession {

    /**
     * get a session object by key.
     * @param key
     * @return
     * @throws IllegalServiceException
     */
    Object get(String key) throws IllegalServiceException;


    /**
     * put a session condition by timeout
     * @param key
     * @param val
     * @param millis
     * @throws IllegalServiceException
     */
    void put(String key, Object val, long millis) throws IllegalServiceException;

    /**
     * the session has or not.
     * @param key
     * @return
     * @throws IllegalServiceException
     */
    boolean has(String key) throws IllegalServiceException;

    /**
     * delete a session.
     * @param key
     * @throws IllegalServiceException
     */
    void remove(String key) throws IllegalServiceException;

    /**
     * get session count.
     * @return
     * @throws IllegalServiceException
     */
    int count() throws IllegalServiceException;

    /**
     * clear all session.
     * @throws IllegalServiceException
     */
    void clear() throws IllegalServiceException;

    /**
     * return a session id.
     * @return
     * @throws IllegalServiceException
     */
    String getId() throws IllegalServiceException;
}
