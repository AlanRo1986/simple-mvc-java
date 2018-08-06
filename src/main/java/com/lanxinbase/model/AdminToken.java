package com.lanxinbase.model;

import com.lanxinbase.model.basic.Model;

public class AdminToken extends Model {

    private Integer admId;

    private String token;

    public Integer getAdmId() {
        return admId;
    }

    public void setAdmId(Integer admId) {
        this.admId = admId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
