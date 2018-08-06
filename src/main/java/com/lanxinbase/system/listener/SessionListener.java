package com.lanxinbase.system.listener;

import com.lanxinbase.system.basic.AbstractCompact;
import com.lanxinbase.system.core.Application;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.provider.basic.DCacheData;
import com.lanxinbase.system.provider.basic.ISession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;


import javax.servlet.http.*;
import java.util.Enumeration;

/**
 * Created by alan.luo on 2017/9/26.
 */
public class SessionListener extends AbstractCompact implements ISession {

    @Autowired
    private HttpSession session;

    private static final long defaultMillis = 3600000L;

    public SessionListener() {
        super(SessionListener.class);

        this.contextInitialized();
    }

    public void contextInitialized() {
        AutowireCapableBeanFactory factory = Application.getInstance(null).getContext().getAutowireCapableBeanFactory();
        factory.autowireBeanProperties(this,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,true);
        factory.initializeBean(this,"sessionListener");
    }

    public HttpSession getSession() {
        return session;
    }

    @Override
    public Object get(String key) throws IllegalServiceException {
        DCacheData data = (DCacheData) session.getAttribute(key);
        if (data != null && data.getExpired() > System.currentTimeMillis()){
            return data.getVal();
        }
        remove(key);
        return null;
    }

    @Override
    public void put(String key, Object val, long millis) throws IllegalServiceException {
        if (millis <= 0){
            millis = defaultMillis;
        }else {
            millis *= 1000L;
        }

        DCacheData data = new DCacheData(val,System.currentTimeMillis() + millis);
        session.setAttribute(key,data);
    }

    @Override
    public boolean has(String key) throws IllegalServiceException {
        return session.getAttribute(key) != null;
    }

    @Override
    public void remove(String key) throws IllegalServiceException {
        session.removeAttribute(key);
    }

    @Override
    public int count() throws IllegalServiceException {
        Enumeration<String> names = session.getAttributeNames();
        int i = 0;
        while (names.hasMoreElements()){
            i++;
            names.nextElement();
        }
        return i;
    }

    @Override
    public void clear() throws IllegalServiceException {
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()){
            session.removeAttribute(names.nextElement());
        }
    }

    @Override
    public String getId() throws IllegalServiceException {
        return session.getId();
    }
}
