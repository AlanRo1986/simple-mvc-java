package com.lanxinbase.system.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan.luo on 2017/9/18.
 */
public abstract class ExceptionError extends Exception {

    private Integer code = 0;

    public static final Integer success = 1;
    public static final Integer error = 0;
    public static final Integer error_user_registered = 2;
    public static final Integer error_updated = 3;
    public static final Integer error_has_not_data = 4;
    public static final Integer error_userId_must_be_require = 5;
    public static final Integer error_has_not_username = 6;
    public static final Integer error_has_not_account = 7;
    public static final Integer error_user_password_invalid = 8;
    public static final Integer error_has_not_logined = 9;
    public static final Integer error_argument_invalid = 10;


    public static final Integer error_inserted= 13;

    public static final Integer error_recharge_outof_money= 14;
    public static final Integer error_recharge_init= 15;
    public static final Integer error_recharge_sign_fault = 16;
    public static final Integer error_recharge_appId_invalid = 17;
    public static final Integer error_recharge_mchId_invalid = 18;

    public static final Integer error_has_not_raw_data = 19;
    public static final Integer error_recharge_money_invalid = 20;

    public static final Integer error_params_require_mobile = 21;
    public static final Integer error_params_require_sms_code = 22;
    public static final Integer error_params_sms_code_expireIn = 23;
    public static final Integer error_get_sms_code_later = 24;
    public static final Integer error_require_params= 25;
    public static final Integer error_data_already_exist= 26;
    public static final Integer error_account_has_not_more_money = 27;




    /**
     * 用户权限错误码
     */
    public static final Integer error_unauthorized_access = 4010;
    public static final Integer error_user_token_invalid = 4011;
    public static final Integer error_unauthorized_access_2 = 4012;
    public static final Integer error_unauthorized_invalid = 4014;
    public static final Integer error_need_logined = 4015;

    public static final Integer error_access_denied = 4019;


    private static final Map<Integer,String> errors = new HashMap<Integer,String>(){
        {
            put(success,"ok");
            put(error,"error");

            put(error_require_params,"请求参数错误或格式不正确");
            put(error_get_sms_code_later,"您点击太快了,请稍候再试!");
            put(error_params_sms_code_expireIn,"手机验证码已过期");
            put(error_params_require_sms_code,"请输入手机验证码");
            put(error_recharge_outof_money,"支付金额应该>10万");
            put(error_recharge_init,"初始化订单失败");
            put(error_recharge_sign_fault,"支付订单签名错误");
            put(error_recharge_appId_invalid,"支付接口无效");
            put(error_recharge_mchId_invalid,"支付商户无效");
            put(error_has_not_raw_data,"没有原始数据");
            put(error_recharge_money_invalid,"响应的金额与原始数据不一致");
            put(error_params_require_mobile,"请输入正确的手机号码");
            put(error_data_already_exist,"数据已存在");
            put(error_account_has_not_more_money,"账号余额不足，请充值！");


            put(error_user_registered,"error_user_registered");
            put(error_updated,"error_updated");
            put(error_has_not_data,"error_has_not_data");
            put(error_userId_must_be_require,"error_userId_must_be_require");
            put(error_has_not_username,"error_has_not_username");
            put(error_has_not_account,"error_has_not_account");
            put(error_user_password_invalid,"error_user_password_invalid");
            put(error_has_not_logined,"error_has_not_logined");
            put(error_argument_invalid,"error_argument_invalid");
            put(error_need_logined,"error_need_logined");
            put(error_access_denied,"error_access_denied");
            put(error_unauthorized_access,"error_unauthorized_access");
            put(error_user_token_invalid,"error_user_token_invalid");
            put(error_inserted,"error_inserted");
            put(error_unauthorized_access_2,"error_unauthorized_access_else");
            put(error_unauthorized_invalid,"error_unauthorized_invalid");
        }
    };

    public ExceptionError(String error){
        super(error);
    }

    public ExceptionError(Integer code){
        super(errors.get(code));
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
