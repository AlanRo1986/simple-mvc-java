package com.lanxinbase.system.provider;


import com.lanxinbase.system.provider.basic.HttpRequestData;
import com.lanxinbase.system.provider.handler.HttpConnector;
import com.lanxinbase.system.annotation.Provider;
import com.lanxinbase.system.utils.JsonUtil;

import java.util.Map;

/**
 * Created by alan.luo on 2017/7/27.
 */
@Provider
public class HttpProvider extends HttpConnector {

    public HttpProvider(){
        super();
    }

    public static HttpProvider getInstance(){
        return new HttpProvider();
    }

    /**
     * get
     * @param url
     * @param data
     * @return
     */
    public String get(String url,HttpRequestData data){
        data.setMethod(GET);
        return  this.go(url,data);
    }


    /**
     * post
     * @param url
     * @param data
     * @return
     */
    public String post(String url,HttpRequestData data){
        data.setMethod(POST);
        return  this.go(url,data);
    }

    /**
     *
     * @param url
     * @param data
     * @return
     */
    private final String go(String url,HttpRequestData data){
        return this.curl(url,data);
    }

}
