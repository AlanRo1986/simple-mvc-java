package com.lanxinbase.system.core;

import com.lanxinbase.constant.Constant;

/**
 * Created by alan.luo on 2017/9/18.
 */
public class ResultResp<T> {

    /**
     * response error code to client
     */
    private int code = 0;

    /**
     * response error message to client
     */
    private String info = "";

    /**
     * response data to client.
     */
    private T data = null;

    public ResultResp() {
        setCode(1);
        setInfo("ok");
    }

    public ResultResp(Integer code,String info) {
        setCode(code);
        setInfo(info);
    }


    public int getCode() {
        return code;
    }

    public ResultResp<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = getLang(info);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 本地化语言
     * @param name
     * @return
     */
    public String getLang(String name){
        if(name == null){
            return null;
        }

        if (name.indexOf("error") == -1 && name.indexOf("info") == -1){
            return name;
        }
        return Application.getInstance(null).getLang(name,name.indexOf("error") == -1 ? Constant.langTypeInfo : Constant.langTypeErrors);
    }

}
