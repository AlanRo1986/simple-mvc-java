package com.lanxinbase.system.api.wx;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.lanxinbase.system.exception.IllegalValidateException;
import com.lanxinbase.system.provider.HttpV2Provider;
import com.lanxinbase.system.provider.basic.HttpRequestData;
import com.lanxinbase.system.provider.basic.Response;
import com.lanxinbase.system.utils.CommonUtils;
import com.lanxinbase.system.utils.DateTimeUtils;
import com.lanxinbase.system.utils.MessageDigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by alan.luo on 2017/8/24.
 */
public class WxPayHandler implements IWxPayHandler {

    private Logger logger = Logger.getLogger(getClass().getName());
    private WxConfig wxConfig;
    private WXPay wxPay;

    public WxPayHandler() {

    }

    /**
     * 实例化微信支付API接口
     *
     * @param payType 终端类型:APP|JSAPI|WEB
     */
    public WxPayHandler(String payType) {
        wxConfig = new WxConfig(payType);
        wxPay = new WXPay(wxConfig);
    }

    public static WxPayHandler newInstance(String payType) {
        return new WxPayHandler(payType);
    }


    /**
     * 统一下单-APP
     *
     * @param paySn   支付流水号号
     * @param money   支付金额
     * @param subject 支付的标题
     * @return
     */
    @Override
    public Map<String, String> submit(String paySn, double money, String subject) throws Exception {
        return submit(paySn, money, subject, null);
    }


    /**
     * 统一下单
     *
     * @param paySn   支付流水号号
     * @param money   支付金额
     * @param subject 支付的标题
     * @param openid  null|string 如果是payType != APP那么必须传入openid
     * @return Map对象
     */
    @Override
    public Map<String, String> submit(String paySn, double money, String subject, String openid) throws Exception {
        Map<String, String> data = buildMap(paySn, String.valueOf(CommonUtils.moneyFormatToInt(money)), subject, openid);
        Map<String, String> res = wxPay.fillRequestData(data);
        return wxPay.unifiedOrder(res);
    }


    /**
     * 微信退款下单
     *
     * @param newPaySn 支付流水号
     * @param oldPaySn 旧的商户支付流水号
     * @param targetSn 旧的微信支付流水号
     * @param money    退款金额
     * @param totalFee 订单金额
     * @param subject  提示标题
     * @return
     */
    @Override
    public Map<String, String> refund(String newPaySn, String oldPaySn, String targetSn, double money, double totalFee, String subject) throws Exception {
        Map<String, String> data = new HashMap<>();

        data.put("transaction_id", targetSn);//微信订单号
        data.put("appid", wxConfig.getAppID());//微信订单号
        data.put("mch_id", wxConfig.getMchID());//微信订单号
        data.put("out_trade_no", oldPaySn);//商户订单号
        data.put("out_refund_no", newPaySn);
        data.put("total_fee", String.valueOf(CommonUtils.moneyFormatToInt(totalFee)));
        data.put("refund_fee", String.valueOf(CommonUtils.moneyFormatToInt(money)));
        data.put("refund_desc", subject);
        data.put("nonce_str", MessageDigestUtils.md5(String.valueOf(DateTimeUtils.getTime())));

        String xml = "<xml>"
                + "<appid>" + data.get("appid") + "</appid>"
                + "<mch_id>" + data.get("mch_id") + "</mch_id>"
                + "<nonce_str>" + data.get("nonce_str") + "</nonce_str>"
                + "<out_trade_no>" + oldPaySn + "</out_trade_no>"
                + "<out_refund_no>" + newPaySn + "</out_refund_no>"
                + "<transaction_id>" + data.get("transaction_id") + "</transaction_id>"
                + "<refund_fee>" + data.get("refund_fee") + "</refund_fee>"
                + "<total_fee>" + data.get("total_fee") + "</total_fee>"
                + "<sign>" + getWxAppSign(data) + "</sign>"
                + "</xml>";


        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(wxConfig.getKeyStore()));
        try {
            keyStore.load(instream, wxConfig.getMchID().toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, wxConfig.getMchID().toCharArray())
                .build();

        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            HttpPost httpPost = new HttpPost(wxConfig.getUrlRefund());

            System.out.println("executing request" + httpPost.getRequestLine());

            StringEntity se = new StringEntity(xml);
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                    String text;
                    String res = "";

                    while ((text = bufferedReader.readLine()) != null) {
                        res += text;
                    }

                    Map<String, String> resData = WXPayUtil.xmlToMap(res);
                    data.put("result_code", resData.containsKey("result_code") ? resData.get("result_code") : "FAIL");
                    data.put("return_msg", data.get("result_code").equals("FAIL") ? "操作失败" : "操作成功");
                    data.put("refund_id", resData.containsKey("refund_id") ? resData.get("refund_id") : "");
                    data.put("resJson", res);
                    if (resData.containsKey("return_msg")) {
                        data.put("return_msg", resData.get("return_msg"));
                    }
                    if (resData.containsKey("err_code")) {
                        data.put("return_msg", resData.get("err_code_des"));
                    }

                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        return data;
    }


    /**
     * 绑定统一下单数据
     *
     * @param paySn   支付流水号
     * @param money   金额
     * @param subject 标题
     * @param openid  如果是JSAPI|WEB支付，那么则需要传入此openid
     * @return 返回map对象
     */
    @Override
    public Map<String, String> buildMap(String paySn, String money, String subject, String openid) {
        Map<String, String> data = new HashMap<>();

        data.put("body", subject);
        //data.put("detail","CANTO-充值");//商品详情
        data.put("out_trade_no", paySn);//订单号
        data.put("fee_type", "CNY");
        data.put("total_fee", money);//订单总金额，单位为分
        data.put("spbill_create_ip", CommonUtils.getClientIp(null));//终端IP
        data.put("notify_url", wxConfig.getNOTIFY_URL());//回调地址
        data.put("trade_type", wxConfig.getWxPayType());//交易类型APP支付 APP|JSAPI
        if (openid != null) {
            data.put("openid", openid);
        }
        return data;
    }


    /**
     * 登录授权
     *
     * @param code
     * @return
     */
    @Override
    public String oauth2(String code) throws Exception {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalValidateException("参数[code]错误");
        }

        String url = String.format(WxConfig.API.Oauth2Url, code);

        Map<String, Object> data = curl(url);

        if (data.containsKey("openid")) {
            return data.get("openid").toString();
        }

        return "";
    }

    @Override
    public Map<String, Object> doGetOauth2(String code) throws Exception {
        String url = String.format(WxConfig.API.Oauth2Url, code);
//        { "access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", "scope":"SCOPE" }
        return curl(url);
    }


    /**
     * 获取微信用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String accessToken, String openId) {
        String url = String.format(WxConfig.API.userUrl, accessToken, openId);
        return curl(url);
    }

    public void setPayType(String payType) {
        wxConfig.setWxPayType(payType);
    }

    public WxConfig getWxConfig() {
        return wxConfig;
    }

    private Map<String, Object> curl(String url) {
        HttpRequestData data = new HttpRequestData();
        HttpV2Provider http = HttpV2Provider.newInstance();
        Response res = http.get(url, data);
        logger.info(">>>>>>>>" + res.getRaw());
        return res.getData();
    }

    /**
     * 懒得写，微信退款的签名
     *
     * @param map
     * @return
     */
    protected String getWxAppSign(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("appid=" + map.get("appid") + "&");
        sb.append("mch_id=" + map.get("mch_id") + "&");
        sb.append("nonce_str=" + map.get("nonce_str") + "&");
        sb.append("out_refund_no=" + map.get("out_refund_no") + "&");
        sb.append("out_trade_no=" + map.get("out_trade_no") + "&");
        sb.append("refund_fee=" + map.get("refund_fee") + "&");
        sb.append("total_fee=" + map.get("total_fee") + "&");
        sb.append("transaction_id=" + map.get("transaction_id") + "&");
        sb.append("key=" + getWxConfig().getKey());
        String sign = MessageDigestUtils.md5(sb.toString()).toUpperCase();
        //appid=wx9eefb5cdb99716d6&mch_id=1494650512&nonce_str=50e6a4d9f106b33ab086950e6f2ce365&out_refund_no=R100201712126950468283114&out_trade_no=100201712126950468283114&
        // refund_fee=50000&total_fee=50000&transaction_id=20171219
        return sign;
    }


}
