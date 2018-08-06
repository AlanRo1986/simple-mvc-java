package com.lanxinbase.system.api.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lanxinbase.constant.ConstantApi;
import com.lanxinbase.system.utils.CommonUtils;

import java.util.Map;

public class AliPayHandler implements IAliPayHandler {

    private AlipayClient alipayClient;

    private AliConfig aliConfig;

    public AliPayHandler(){
        aliConfig = new AliConfig();
    }

    public AliPayHandler(AliConfig config){
        this.aliConfig = config;
    }

    public static AliPayHandler newInstance(){
        return new AliPayHandler();
    }


    /**
     * 支付宝支付-统一下单
     * @param paySn 支付宝流水号
     * @param money 支付金额
     * @param subject 支付标题
     * @return AlipayTradeAppPayResponse
     * @throws Exception
     */
    @Override
    public AlipayTradeAppPayResponse submit(String paySn, double money, String subject) throws Exception {
        return this.submit(paySn,money,subject,null);
    }

    /**
     * 支付宝支付-统一下单
     * @param paySn 支付宝流水号
     * @param money 支付金额
     * @param subject 支付标题
     * @param openid 预留字段
     * @return AlipayTradeAppPayResponse
     * @throws Exception
     */
    @Override
    public AlipayTradeAppPayResponse submit(String paySn, double money, String subject, String openid) throws Exception {

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        request.setBizModel(this.buildMap(paySn,money,subject,openid));
        request.setNotifyUrl(ConstantApi.PayNotify.ALIPAY);

        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response;
    }

    /**
     * 支付宝退款接口
     * @param newPaySn 新的支付流水号
     * @param oldPaySn 要退款的支付流水号
     * @param targetSn 要退款的支付宝支付流水号
     * @param money 退款金额
     * @param subject 退款标题
     * @return AlipayTradeRefundResponse
     * @throws Exception
     */
    @Override
    public AlipayTradeRefundResponse refund(String newPaySn, String oldPaySn, String targetSn, double money, String subject) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                this.aliConfig.getApiUrl(),
                this.aliConfig.getAppId(),
                this.aliConfig.getAppPrivateKey(),
                "json",
                "UTF-8",
                this.aliConfig.getAlipayPublicKey2(),
                this.aliConfig.getSignType());

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+oldPaySn+"\"," +
                "\"trade_no\":\""+targetSn+"\"," +
                "\"refund_amount\":"+money+"," +
                "\"refund_reason\":\""+subject+"\"," +
                "\"out_request_no\":\""+newPaySn+"\"" +
                "  }");
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        return response;
    }

    @Override
    public boolean rsaCheck(Map<String, String> params) throws AlipayApiException {
        boolean flag = AlipaySignature.rsaCheckV1(params, this.aliConfig.getAlipayPublicKey2(), "utf-8","RSA2");
        //boolean flag = AlipaySignature.rsaCheckV2(params,alipayPulicKey2,"utf-8","RSA2");
        return flag;
    }

    @Override
    public AlipayTradeAppPayModel buildMap(String paySn, double money, String subject, String openid) {

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(subject);
        model.setOutTradeNo(paySn);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(CommonUtils.moneyFormat(money));

        //model.setProductCode("QUICK_MSECURITY_PAY");
        //model.setBody("subject");
        return model;
    }

    @Override
    public String oauth2(String code) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> doGetOauth2(String code) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String accessToken, String openId) {
        return null;
    }
}
