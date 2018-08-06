package com.lanxinbase.system.provider.basic;

/**
 * Created by alan.luo on 2017/8/4.
 */
public interface IStoreFile {
    /**
     * get a file.
     * @param path
     * @return
     */
    String get(String path);

    /**
     * append content to the file.
     * @param path
     * @param content
     */
    void put(String path, String content);

    /**
     * create a new file on the local disk.
     * @param path
     * @param content
     */
    void save(String path, String content);

    /**
     * delete a file.
     * @param path
     */
    boolean remove(String path);

    /**
     * is available or not.
     * @param path
     * @return
     */
    boolean exist(String path);
}
