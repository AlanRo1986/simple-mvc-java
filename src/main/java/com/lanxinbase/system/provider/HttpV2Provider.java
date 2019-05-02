package com.lanxinbase.system.provider;


import com.lanxinbase.system.annotation.Provider;
import com.lanxinbase.system.provider.handler.HttpV2Connector;

/**
 * Created by alan.luo on 2019/01/04.
 */
@Provider
public class HttpV2Provider extends HttpV2Connector {

    public HttpV2Provider(){
        super();
    }

    public static HttpV2Provider newInstance(){
        return new HttpV2Provider();
    }
}
