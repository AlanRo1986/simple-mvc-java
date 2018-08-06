package com.lanxinbase.system.provider.basic;


import com.lanxinbase.system.provider.handler.HttpConnector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan.luo on 2017/7/27.
 */
public class HttpRequestData {

    private String method = HttpConnector.GET;

    private Map<String,Object> param = null;
    private Map<String,Object> cookie = null;
    private Map<String,String> header = null;

    private String body = null;

    public HttpRequestData(){
        this.param = new HashMap<>();
        this.cookie = new HashMap<>();
        this.header = new HashMap<>();
    }


    public Map<String, Object> getParam() {
        return param;
    }

    public HttpRequestData setParam(String key, Object val) {
        this.param.put(key,val);
        return this;
    }

    public Map<String, Object> getCookie() {
        return cookie;
    }

    public HttpRequestData setCookie(String key, Object val) {
        this.cookie.put(key,val);
        return this;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public HttpRequestData setHeader(String key, String val) {
        this.header.put(key,val);
        return this;
    }
}
