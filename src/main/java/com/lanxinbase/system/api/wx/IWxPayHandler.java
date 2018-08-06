package com.lanxinbase.system.api.wx;

import java.util.Map;

public interface IWxPayHandler {

    Map<String, String> submit(String paySn, double money, String subject) throws Exception;

    Map<String, String> submit(String paySn, double money, String subject, String openid) throws Exception;

    Map<String,String> refund(String newPaySn, String oldPaySn, String targetSn, double money, double totalFee, String subject) throws Exception;

    Map<String,String> buildMap(String paySn, String money, String subject, String openid);

    String oauth2(String code) throws Exception;

    Map<String,Object> doGetOauth2(String code) throws Exception;

    Map<String,Object> getUserInfo(String accessToken, String openId);

}
