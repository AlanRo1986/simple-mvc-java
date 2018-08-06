package com.lanxinbase.constant;


/**
 * TODO 此文件在编译的时候需要检查 DOMAIN/DOMAIN_IMG/SocketPort1/uploadDirectory/MYSQL_URL/MYSQL_PASSWORD
 * Created by alan.luo on 2017/9/21.
 */
public class Constant {

    /**
     * API notify domain.
     */
    public static final String DOMAIN = "http://www.lanxinbase.com/";

    public static final String DOMAIN_IMG = "http://www.lanxinbase.com/upload/";

    public static final String DOMAIN_API = DOMAIN + "lanxinbase/api/";

    public static final String DOMAIN_CHECK_QRCODE =  "http://www.lanxinbase.com/check.html?paySn=";

    public static final String TITLE =  "海舞文旅";

    /**
     * upload image save file directory.
     */
    public static final String uploadDirectory = "/alidata/www/upload";

    public static final String[] uploadAllowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };


    /**
     * mysql data configuration。
     */
    public static final String MYSQL_URL= "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT&useSSL=false&characterEncoding=utf8";
    public static final String MYSQL_PASSWORD = "root";



//**********************************************************************************************************************
//============================================static final==============================================================
    /**
     * page size.
     */
    public static final long PAGE_SIZE = 10;
    public static final long PAGE_SIZE_ADMIN = 40;

    /**
     * locale config
     */
    public static final String defaultLocale = "zh_CN";

    public static final String hashKey = "8623CCDDCED055AE31B9CFDCEC773BAC";

    public static final String tokenKey = "token";
    public static final int tokenExpireIn = 5184000;

    /**
     * 上传文件的大小(单位是B)
     */
    public static final int uploadMaxSize = 51200000;
    public static final float uploadImageOptimize = 0.8f; //上传图片的质量 0.1~1之间


    /**
     * 本地化语言
     */
    public static final String langTypeInfo = "lang";
    public static final String langTypeErrors = "errors";

}
