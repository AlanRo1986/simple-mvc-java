package com.lanxinbase.system.provider.handler;

import com.lanxinbase.system.provider.basic.HttpRequestData;
import com.lanxinbase.system.provider.basic.Response;

import java.io.File;
import java.io.InputStream;

/**
 * Created by alan on 2019/5/2.
 */
public interface IHttpConnector {
    InputStream exec(String url, HttpRequestData data);

    InputStream raw(String url, HttpRequestData data);

    String http(String url, HttpRequestData data);

    File download(String url, HttpRequestData data, String filePath);

    File download(String url, HttpRequestData data, File file);

    Response get(String url, HttpRequestData data);

    Response post(String url,HttpRequestData data);

    Response put(String url,HttpRequestData data);

    Response delete(String url,HttpRequestData data);

    void close();

}
