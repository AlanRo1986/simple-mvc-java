package com.lanxinbase.system.api.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import java.util.Map;

public interface IAliPayHandler {

    AlipayTradeAppPayResponse submit(String paySn, double money, String subject) throws Exception;

    AlipayTradeAppPayResponse submit(String paySn, double money, String subject, String openid) throws Exception;

    AlipayTradeRefundResponse refund(String newPaySn, String oldPaySn, String targetSn, double money, String subject) throws Exception;

    boolean rsaCheck(Map<String, String> params) throws AlipayApiException;

    AlipayTradeAppPayModel buildMap(String paySn, double money, String subject, String openid);

    String oauth2(String code) throws Exception;

    Map<String,Object> doGetOauth2(String code) throws Exception;

    Map<String,Object> getUserInfo(String accessToken, String openId);

}
