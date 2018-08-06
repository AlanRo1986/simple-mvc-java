package com.lanxinbase.constant;

public class ConstantApi {

    /**
     * 短信配置文件
     */
    public static class SmsConfig{
        public static final String SmsAccessKeyId = "LTAII11eWg";
        public static final String SmsAccessKeySecret = "yBZuXnn0rCkAZBD1Or";

        public static final String SMS_SIGN = "短信签名";

        public static final String RESET_MOBILE = "SMS_1384518";
        public static final String LOGIN = "SMS_134531";

        public static final String NOTIFY_DEAL_SUCC_PAR = "{\"code\":\"%s\"}";


    }

    /**
     * 支付配置项
     */
    public static class PayConfig{
        public static final String PAY_WEIXIN_APP = "wxPay";//微信
        public static final String PAY_WEIXIN_JS = "JSAPI";//微信
        public static final String PAY_ALIPAY = "aliPay";//支付宝
        public static final String PAY_WALLET = "wallet";//钱包支付

        public static final String keyStore = "/www/key/";//退款的证书
    }

    public static class PayNotify{
        public static final String WXPAY = Constant.DOMAIN_API + "notifyWx";//微信
        public static final String ALIPAY = Constant.DOMAIN_API + "notifyAli";//支付宝
    }

    public static final class BaiduMap{
        public static final String AK = "iPZ3X5Gf3f3gTme16LmzTNvFtCwfgMR3";
        public static final String geocoder = "http://api.map.baidu.com/geocoder/v2/?location=%s,%s&output=json&ak="+AK;
    }

}
