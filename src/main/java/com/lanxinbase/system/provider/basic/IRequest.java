package com.lanxinbase.system.provider.basic;

import javax.servlet.http.*;

/**
 * Created by alan.luo on 2017/9/26.
 */
public interface IRequest {

    String get(String key);

    Object getParameter(String key);

    String getController();

    String getAction();

    String getMethod();

    Cookie getCookie(String key);

    String getHeader(String key);

    String getPathInfo();

    String getQueryString();

    String getRequestedSessionId();

    HttpSession getSession(boolean var1);

    HttpSession getSession();

    String getClientIp();

    String getRequestURL();

    String getContextType();

    int getContentLength();

    String getLocale();

}
