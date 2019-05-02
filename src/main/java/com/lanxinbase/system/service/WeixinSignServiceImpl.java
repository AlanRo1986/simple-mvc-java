package com.lanxinbase.system.service;

import com.lanxinbase.constant.ConstantConf;
import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.core.Application;
import com.lanxinbase.system.provider.CacheProvider;
import com.lanxinbase.system.provider.HttpV2Provider;
import com.lanxinbase.system.provider.basic.HttpRequestData;
import com.lanxinbase.system.provider.basic.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

@Service
public class WeixinSignServiceImpl extends CompactService {

    private static final String KEY_JSAPI_TICKET = "wx_g_jsapi_ticket";
    private static final String KEY_ACCESSTOKEN_KEY = "wx_g_access_token";
    private static final String URL_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=%s";
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    @Autowired
    private CacheProvider cacheProvider;

    @Autowired
    private HttpV2Provider httpV2Provider;

    public WeixinSignServiceImpl() {
        super(WeixinSignServiceImpl.class);
    }

//    public static void main(String[] args) {
//        String jsapi_ticket = "jsapi_ticket";
//
//        // 注意 URL 一定要动态获取，不能 hardcode
//        String url = "http://example.com";
//        Map<String, String> ret = sign(jsapi_ticket, url);
//        for (Map.Entry entry : ret.entrySet()) {
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
//    };

    public Map<String, String> sign(String url) {
        String jsapi_ticket = this.getJsApiTicket();

        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
//        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", Application.getInstance(null).getConf(ConstantConf.CONF_WEIXIN_G_APPID));

        return ret;
    }

    public String getJsApiTicket() {
        String ticket = (String) cacheProvider.get(KEY_JSAPI_TICKET);
        if (ticket == null) {
            String accessToken = this.getAccessToken();
            Response res = httpV2Provider.get(String.format(URL_TICKET, accessToken), new HttpRequestData());

            Map<String, Object> resMap = res.getData();
            if (resMap != null && resMap.containsKey("ticket")) {
                ticket = resMap.get("ticket").toString();
                cacheProvider.put(KEY_JSAPI_TICKET, ticket, 7190L);
            }
        }
        return ticket;
    }

    public String getAccessToken() {
        String accessToken = (String) cacheProvider.get(KEY_ACCESSTOKEN_KEY);
        if (accessToken == null) {
            String appId = Application.getInstance(null).getConf(ConstantConf.CONF_WEIXIN_G_APPID);
            String secret = Application.getInstance(null).getConf(ConstantConf.CONF_WEIXIN_G_APPSECRET);

            Response res = httpV2Provider.get(String.format(URL_ACCESS_TOKEN, appId, secret), new HttpRequestData());

            Map<String, Object> resMap = res.getData();
            if (resMap != null && resMap.containsKey("access_token")) {
                accessToken = resMap.get("access_token").toString();
                cacheProvider.put(KEY_ACCESSTOKEN_KEY, accessToken, 7190L);
            }
        }

        return accessToken;
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
