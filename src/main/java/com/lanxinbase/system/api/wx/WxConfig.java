package com.lanxinbase.system.api.wx;

import com.github.wxpay.sdk.WXPayConfig;
import com.lanxinbase.constant.ConstantApi;

import java.io.InputStream;

public class WxConfig implements WXPayConfig {

    private final int HTTP_READ_TIMEOUT = 10 * 1000;
    private final int HTTP_CONNECT_TIMEOUT = 6 * 1000;

    private static final String apiUrl = "https://api.weixin.qq.com/";

    /**
     * 微信支付商户号
     */
    private final String Mch_ID = "aaa";

    /**
     * 可以在线生成，API加密密钥
     *
     */
    private final String API_KEY = "c3fa370894900sdfh53452039b";

    /**
     * APP配置信息
     */
    private class APP{
        static final  String APP_ID = "333";
        static final  String APP_SerKey = "333";
    }

    /**
     * 小程序|公众号
     */
    public static class JSAPI{
        static final String APP_ID = "wx9aae345363e59219";
        static final String APP_SerKey = "5ef0f8112ba345345560fc43";

//        static final String APP_ID = "wx1eba3603c48f3922";//test
//        static final String APP_SerKey = "ef629ab61a16563172610a37f7a53d97";//test
    }

    public class API{
        /**
         * 获取微信用户信息
         */
        public static final String userUrl = apiUrl + "sns/userinfo?access_token=%s&openid=%s";

        /**
         * 小程序登录授权url地址
         */
//        public static final String Oauth2Url =  apiUrl + "sns/jscode2session?appid="+ JSAPI.APP_ID+"&secret="+ JSAPI.APP_SerKey+"&js_code=%s&grant_type=authorization_code";

        /**
         * 公众号授权地址
         */
        public static final String Oauth2Url =  apiUrl + "sns/oauth2/access_token?appid="+ JSAPI.APP_ID+"&secret="+ JSAPI.APP_SerKey+"&code=%s&grant_type=authorization_code";

    }

    /**
     * 微信测试回调地址
     */
    private final String NOTIFY_URL = ConstantApi.PayNotify.WXPAY;

    /**
     * 退款的证书
     */
    private final String keyStore = ConstantApi.PayConfig.keyStore+"apiclient_cert_haiwu.p12";

    private final String urlRefund = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    private String wxPayType;

    public WxConfig(){

    }

    /**
     * 实例化
     * @param wxType JSAPI|APP
     */
    public WxConfig(String wxType){
        this.setWxPayType(wxType);
    }

    @Override
    public String getAppID() {
        if (ConstantApi.PayConfig.PAY_WEIXIN_JS.equals(getWxPayType())){
            return JSAPI.APP_ID;
        }else {
            return APP.APP_ID;
        }
    }

    @Override
    public String getMchID() {
        return this.Mch_ID;
    }

    @Override
    public String getKey() {
        return this.API_KEY;
    }

    /**
     * 获取商户证书内容
     * @return
     */
    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return HTTP_CONNECT_TIMEOUT;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return HTTP_READ_TIMEOUT;
    }


    public String getApiUrl() {
        return apiUrl;
    }

    public String getMch_ID() {
        return Mch_ID;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getNOTIFY_URL() {
        return NOTIFY_URL;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public String getUrlRefund() {
        return urlRefund;
    }

    public String getWxPayType() {
        return wxPayType;
    }

    public void setWxPayType(String wxPayType) {
        this.wxPayType = wxPayType;
    }




}
