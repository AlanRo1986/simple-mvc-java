package com.lanxinbase.system.provider.basic;

import com.lanxinbase.system.utils.JsonUtil;

import java.util.Map;

/**
 * Created by alan on 2019/5/2.
 */
public class Response {


    private Integer code;

    private String info;

    private String raw;

    private Map<String, Object> data;

    public Response() {
        this(1, "ok");
    }

    public Response(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
        if (raw != null && JsonUtil.isJson(raw)) {
            this.setData(JsonUtil.JsonToMap(raw));
        }
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
