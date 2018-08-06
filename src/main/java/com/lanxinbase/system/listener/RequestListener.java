package com.lanxinbase.system.listener;

import com.lanxinbase.constant.Constant;
import com.lanxinbase.system.basic.CompactProvider;
import com.lanxinbase.system.core.Application;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.provider.basic.IRequest;
import com.lanxinbase.system.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by alan.luo on 2017/9/26.
 *
 * RequestListener requestListener = new RequestListener();
 * requestListener.get("action");
 * requestListener.getAction();
 * requestListener.getClientIp();
 * requestListener.getMethod();
 * requestListener.getController();
 * requestListener.getContentLength();
 * requestListener.getContextType();
 *
 */
public class RequestListener extends CompactProvider implements IRequest {

    private static final String locale = "locale";

    @Autowired
    private HttpServletRequest request;

    private Map<String,String> params = null;
    private Map<String,Cookie> cookie = null;
    private Map<String,String> header = null;

    public RequestListener() {
        super(RequestListener.class);
        contextInitialized();
        init(false);
    }

    public static RequestListener getInstance(){
        return new RequestListener();
    }

    public void contextInitialized() {
        AutowireCapableBeanFactory factory = Application.getInstance(null).getContext().getAutowireCapableBeanFactory();
        factory.autowireBeanProperties(this,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,true);
        factory.initializeBean(this,"requestListener");
    }

    public void init(boolean isBody) {
        params = new HashMap<>();
        cookie = new HashMap<>();
        header = new HashMap<>();

        /**
         * initialize the cookies
         */
        if (request.getCookies() != null){
            Cookie[] cookies = request.getCookies();
            for (Cookie c:cookies){
                cookie.put(c.getName(),c);
            }
        }

        /**
         * initialize the request
         */
        Enumeration<String> enums = request.getHeaderNames();
        while (enums.hasMoreElements()){
            String key = enums.nextElement();
            if (request.getHeader(key) != null){
                header.put(key.toLowerCase(),request.getHeader(key));
                params.put(key.toLowerCase(),request.getHeader(key));
            }
        }

        //get&post
        enums = request.getParameterNames();
        while (enums.hasMoreElements()){
            String key = enums.nextElement();
            if (request.getParameter(key) != null){
                params.put(key,request.getParameter(key));
            }
        }

        /**
         * 为了兼容spring body字段映射，所以这个监听类不读取body中的内容
         */
        if (!isBody){
            return;
        }

        ServletInputStream input = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            String line = null;
            input = request.getInputStream();

            reader = new InputStreamReader(input,"utf-8");
            br = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine())!= null){
                sb.append(line);
            }

            Map<String,String> map;
            if (JsonUtil.isJson(sb.toString())){
                /**
                 * 格式比如：{'key':'val',...}
                 * 一级json格式
                 */
                map = JsonUtil.JsonToMaps(sb.toString());
            }else {
                map = parserParams(sb.toString());
            }

            addBodyForParams(map);

        } catch (IOException e) {
            //e.printStackTrace();
        }finally {
            try {
                if (br != null){
                    br.close();
                    reader.close();
                    input.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public String get(String key){
        if (params.containsKey(key)){
            return params.get(key);
        }
        return null;
    }

    @Override
    public String getParameter(String key){
        return request.getParameter(key);
    }

    @Override
    public String getController(){
        return request.getServletPath();
    }

    @Override
    public String getAction(){
        if (get("action") != null){
            return get("action");
        }
        return getMethod();
    }

    /**
     * request method(GET|POST|DELETE|PUT).
     * @return
     * @throws IllegalServiceException
     */
    @Override
    public String getMethod(){
        return request.getMethod().toUpperCase();
    }

    @Override
    public Cookie getCookie(String key){
        if (cookie.containsKey(key)){
            return cookie.get(key);
        }
        return null;
    }

    @Override
    public String getHeader(String key){
        if (header.containsKey(key.toLowerCase())){
            return header.get(key.toLowerCase());
        }
        return null;
    }

    @Override
    public String getPathInfo(){
        return request.getPathInfo();
    }

    @Override
    public String getQueryString(){
        return request.getQueryString();
    }

    @Override
    public String getRequestedSessionId(){
        return request.getRequestedSessionId();
    }

    @Override
    public HttpSession getSession(boolean var1){
        return request.getSession(var1);
    }

    @Override
    public HttpSession getSession(){
        return request.getSession();
    }

    public String getClientIp(){

        String ip = getHeader("x-forwarded-for");
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip = getHeader("Proxy-Client-IP");
        }

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip = getHeader("WL-Proxy-Client-IP");
        }

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip = getHeader("X-Real-IP");
        }

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public String getRequestURL(){
        return request.getRequestURL().toString();
    }

    @Override
    public String getContextType(){
        return request.getContentType();
    }

    @Override
    public int getContentLength(){
        return request.getContentLength();
    }

    @Override
    public String getLocale() {
        //zh_CN
        if (get(locale) != null){
            return get(locale);
        }

        if (StringUtils.isEmpty(request.getLocale().getLanguage()) && StringUtils.isEmpty(request.getLocale().getCountry())){
            return Constant.defaultLocale;
        }

        return request.getLocale().getLanguage() + "_" + request.getLocale().getCountry();
    }

    public HttpServletRequest getRequest() {
        return request;
    }


    /**
     * 处理类似如:id=12&name=admin&passwd=adminpasswd&aggge=280001&set=-1 数据
     * @param string
     * @return
     */
    private Map<String,String> parserParams(String string) {

        Map<String,String> map = new HashMap<>();

        String patten = ".*\\=.*";
        if (string.matches(patten) == false){
            return map;
        }

        String[] str = string.split("&");
        String[] str2;
        if (str.length > 0){
            for (int i=0;i<str.length;i++){
                str2 = str[i].split("=");
                if (str2.length == 2 && !str2[0].isEmpty()){
                    map.put(str2[0],str2[1]);
                }
            }

        }
        return map;
    }

    /**
     * 复制数据到params
     * @param map
     */
    private void addBodyForParams(Map<String, String> map) {
        Set<String> set = map.keySet();
        for (String s:set){
            this.params.put(s,map.get(s));
        }
    }

}
