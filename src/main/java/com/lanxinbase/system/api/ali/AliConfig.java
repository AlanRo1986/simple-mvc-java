package com.lanxinbase.system.api.ali;

public class AliConfig {

    /**
     * 商户号
     */
    private final String appId = "2017";

    /**
     * 接口地址
     */
    private final String apiUrl = "https://openapi.alipay.com/gateway.do";

    /**
     * 签名类型
     */
    private final String signType = "RSA2";

    /**
     * APP私钥
     */
    private final String appPrivateKey = "/++/";

    /**
     * APP公钥
     */
    private final String appPublicKey = "/";

    /**
     * 支付宝公钥
     */
    private final String alipayPublicKey2 = "/";

    public AliConfig(){

    }

    public String getAppId() {
        return appId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getSignType() {
        return signType;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public String getAppPublicKey() {
        return appPublicKey;
    }

    public String getAlipayPublicKey2() {
        return alipayPublicKey2;
    }
}
