package com.lanxinbase.system.pojo;


import com.lanxinbase.model.basic.Model;

public class SendSmsPojo extends Model {

    /**
     * 短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     */
    private String phoneNumbers;

    private String templateCode;

    private String templateParam;

    public SendSmsPojo(){

    }

    public SendSmsPojo(String p,String c,String a){
        this.phoneNumbers = p;
        this.templateCode = c;
        this.templateParam = a;
    }

    public static SendSmsPojo newInstance(String p,String c,String a){
        return new SendSmsPojo(p,c,a);
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}
