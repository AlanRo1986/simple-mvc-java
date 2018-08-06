package com.lanxinbase.system.model;

/**
 * Created by alan on 2018/6/25.
 * 发送短信的模板
 */
public class SmsModel extends Model {
    private String mobile;
    private String templateCode;
    private String param;

    public SmsModel(String m, String c, String p) {
        this.mobile = m;
        this.templateCode = c;
        this.param = p;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
