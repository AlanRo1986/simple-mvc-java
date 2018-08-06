package com.lanxinbase.system.utils;

import com.lanxinbase.constant.ConstantApi;
import com.lanxinbase.constant.ConstantConf;
import com.lanxinbase.system.core.Application;
import com.lanxinbase.system.listener.RequestListener;
import com.lanxinbase.system.provider.HttpProvider;
import com.lanxinbase.system.provider.basic.HttpRequestData;
import com.lanxinbase.system.provider.handler.HttpConnector;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alan.luo on 2017/8/10.
 */
public class CommonUtils {

    public static String getOrderSn(int userId) {
        String str = "800" + userId;
        str += NumberUtils.getRandom(1111, 9999);
        str += String.valueOf(DateUtils.getTime()).substring(5) + "00" + NumberUtils.getRandom(1111, 9999);
        return str;
    }

    /**
     * 取支付编码
     *
     * @return
     */
    public static String getPaySn(int userId) {
        String str = "100" + userId;
        str += NumberUtils.getRandom(1111, 9999);
        str += String.valueOf(DateUtils.getTime()).substring(7) + "00" + NumberUtils.getRandom(1111, 9999);
        return str;
    }


    /**
     * 格式化金钱，返回00.00格式
     *
     * @param money
     * @return
     */
    public static String moneyFormat(Double money) {
        if (money == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(money);
    }

    /**
     * 微信支付使用，将上传过来的金额格式化成0.00格式，然后再乘以100返回以分为单位的的金额
     *
     * @param money
     * @return
     */
    public static int moneyFormatToInt(Double money) {
        DecimalFormat df = new DecimalFormat("##.00");
        return (int) (Double.parseDouble(df.format(money)) * 100);
    }

    /**
     * 格式化数字
     *
     * @param number
     * @param pattern 比如:00.0
     * @return
     */
    public static String formatNumber(Float number, String pattern) {
        if (pattern == null) {
            pattern = "#0.00";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    public static float formatNumber2(Float number, String pattern) {
        if (pattern == null) {
            pattern = "#0.00";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return Float.parseFloat(df.format(number));
    }

    public static Double formatNumber(Double number, String pattern) {
        if (number == null) {
            return 0.00;
        }
        if (pattern == null) {
            pattern = "#0.00";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return Double.valueOf(df.format(number));
    }

    /**
     * 用户手机号码隐藏
     *
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile) {
        return mobile.substring(0, 4) + "****" + mobile.substring(mobile.length()-2);
    }

    public static String hideIdno(String idno) {
        return idno.substring(0, 1) + "************" + idno.substring(idno.length() - 1);
    }

    /**
     * 解析double
     *
     * @param a
     * @return
     */
    public static Double parseDouble(String a) {
        if (StringUtils.isEmpty(a)) {
            return 0.0;
        }

        return Double.parseDouble(a);
    }

    public static String getClientIp(HttpServletRequest request) {

        if (request == null) {
            return RequestListener.getInstance().getClientIp();
        }

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 百度地图云逆地理编码服务
     *
     * @param lat
     * @param lnt
     * @return
     */
    public static String geocoder(double lat, double lnt) {
        String resStr = "";
        HttpRequestData data = new HttpRequestData();
        data.setMethod(HttpConnector.GET);

        HttpProvider httpProvider = (HttpProvider) Application.getInstance(null).getBean(HttpProvider.class);
        String s = httpProvider.get(String.format(ConstantApi.BaiduMap.geocoder, lat, lnt), data);
        
        Map<String, Object> res = JsonUtil.JsonToMap(s);
        if (res != null && res.containsKey("responseData")) {
            res = (Map<String, Object>) res.get("responseData");
            if (res.containsKey("result") && Double.valueOf(res.get("status").toString()) == 0) {
                res = (Map<String, Object>) res.get("result");
                if (res.containsKey("formatted_address") && res.containsKey("sematic_description")) {
                    resStr = res.get("formatted_address").toString() + res.get("sematic_description").toString();
                }
            }
        }
        return resStr;
    }

    public static boolean testMobile(String mobile) {
        if (mobile == null) {
            return false;
        }
        String regx = "^1[3|4|5|6|7|8|9]\\d{1}\\d{8}$";
        return mobile.matches(regx);
    }

    public static boolean testNumber(String num) {
        String regx = "^[0-9]*$";
        return num.matches(regx);
    }

    public static boolean testDate(String date) {
        String regx = "^\\d{4}+\\-\\d{2}+\\-\\d{2}$";
        return date.matches(regx);
    }

    public static boolean testDateTime(String time) {
        String regx = "^\\d{4}+\\-\\d{2}+\\-\\d{2} \\d{2}+\\:\\d{2}+\\:\\d{2}$";
        return time.matches(regx);
    }

    public static boolean testDouble(String test) {
        String regx = "^[-+]?[0-9]+(\\.[0-9]+)?$";
        return test.matches(regx);
    }


    /**
     * 禁言禁语检查
     * @param content
     * @return
     */
    public static boolean testBanned(String content) {
        if (content == null) {
            return false;
        }

        String banned = Application.getInstance(null).getConf(ConstantConf.CONF_TEXT_BANNED);
        if (StringUtils.isEmptyTrim(banned) && !banned.contains(",")) {
            return false;
        }

        boolean has = false;
        String[] strings = banned.split(",");
        for (String s : strings) {
            if (content.contains(s)) {
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * 解析输入过来的EMOJI表情
     * @param str 待处理的字符串
     * @param isUn 是否解码 true|false
     * @return isUn:false则返回字符串,否则返回带有表情的字符串
     */
    public static String parserMoji(String str,boolean isUn) {
        if (StringUtils.isEmptyTrim(str)){
            return str;
        }
        if(isUn){
            return resolveToEmojiFromByte(str);
        }
        return resolveToByteFromEmoji(str);
    }

    public static boolean isNotEmojiCharacter(char codePoint)
    {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 将str中的emoji表情转为byte数组
     *
     * @param str
     * @return
     */
    public static String resolveToByteFromEmoji(String str) {
        Pattern pattern = Pattern
                .compile("[^(\u2E80-\u9FFF\\w\\s`~!@#\\$%\\^&\\*\\(\\)_+-？（）——=\\[\\]{}\\|;。，、《》”：；“！……’:'\"<,>\\.?/\\\\*)]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb2 = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb2, resolveToByte(matcher.group(0)));
        }
        matcher.appendTail(sb2);
        return sb2.toString();
    }

    /**
     * 将str中的byte数组类型的emoji表情转为正常显示的emoji表情
     *
     * @param str
     * @return
     */
    public static String resolveToEmojiFromByte(String str) {
        Pattern pattern2 = Pattern.compile("<:([[-]\\d*[,]]+):>");
        Matcher matcher2 = pattern2.matcher(str);
        StringBuffer sb3 = new StringBuffer();
        while (matcher2.find()) {
            matcher2.appendReplacement(sb3, resolveToEmoji(matcher2.group(0)));
        }
        matcher2.appendTail(sb3);
        return sb3.toString();
    }

    private static String resolveToByte(String str) {
        byte[] b = str.getBytes();
        StringBuffer sb = new StringBuffer();
        sb.append("<:");
        for (int i = 0; i < b.length; i++) {
            if (i < b.length - 1) {
                sb.append(Byte.valueOf(b[i]).toString() + ",");
            } else {
                sb.append(Byte.valueOf(b[i]).toString());
            }
        }
        sb.append(":>");
        return sb.toString();
    }

    private static String resolveToEmoji(String str) {
        str = str.replaceAll("<:", "").replaceAll(":>", "");
        String[] s = str.split(",");
        byte[] b = new byte[s.length];
        for (int i = 0; i < s.length; i++) {
            b[i] = Byte.valueOf(s[i]);
        }
        return new String(b);
    }

    public static String getTimeTips(int i, int day) {
        i = i - day * 86400;
        String res = "";
        if (i <= 0) {
            res = "已结束";
        } else if (i <= 3600 && i > 0) {
            res = CommonUtils.formatNumber(((double) i / 60.0), "#0") + "分钟";
        } else if (i < 86400 && i > 3600) {
            res = CommonUtils.formatNumber(((double) i / 3600.0), "#0.0") + "小时";
        } else if (i >= 86400) {
            res = CommonUtils.formatNumber(((double) i / 86400.0), "#0.0") + "天";
        }
        return res;
    }

    public static String getIdnoSex(String idno) {
        if (StringUtils.isEmpty(idno) || idno.length() != 18) {
            return "未知";
        }
        int i = Integer.parseInt(idno.substring(16, 17));
//        return i % 2 == 0 ? "女士" : "男士";
        return i + "";
    }


}
