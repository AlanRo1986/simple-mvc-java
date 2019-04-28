package com.lanxinbase.system.basic;


import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.exception.IllegalValidateException;
import com.lanxinbase.system.utils.CommonUtils;
import com.lanxinbase.system.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by alan.luo on 2017/9/18.
 */
@Service
public abstract class CompactService extends AbstractCompact {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private HttpSession httpSession;


    public CompactService(){
        super(CompactService.class);
    }

    public CompactService(Class classz){
        super(classz);
    }

    public String formatMoney(double money){
        return CommonUtils.currencyFormat(money);
    }

    private String formatDateTime(String time){
        if (time.length() < 11){
            return "";
        }
        return DateUtils.getFullDateTime(Long.parseLong(time));
    }

    public String formatDateTime(Integer time){
        if (time != null && time > 86400){
            return this.formatDateTime(time+"000");
        }
        return "";
    }

    public int getTime(){
        return DateUtils.getTimeInt();
    }

    /**
     * 因为用户权限在拦截器中就已经拦截过，并且把比较有用的数据保存在session中
     * @return
     * @throws IllegalValidateException 如果没有数据则抛出异常请求登陆
     */
    public Integer getUserId() throws IllegalServiceException{
        String userId = String.valueOf(httpSession.getAttribute("userId"));
        if (StringUtils.isEmpty(userId)){
            throw new IllegalServiceException(ExceptionError.error_unauthorized_invalid);
        }
        return Integer.valueOf(userId);
    }

    public Object getSession(String key) throws IllegalServiceException{
        return httpSession.getAttribute(key);
    }

//    /**
//     * 因为用户权限在拦截器中就已经拦截过，并且把比较有用的数据保存在session中
//     * @return
//     * @throws IllegalValidateException 如果没有数据则抛出异常请求登陆
//     */
//    public HwUserToken getUserToken() throws IllegalServiceException{
//        HwUserToken userToken = (HwUserToken) httpSession.getAttribute(HwUserToken.class.getName());
//        if (userToken == null){
//            throw new IllegalServiceException(ExceptionError.error_unauthorized_invalid);
//        }
//        return userToken;
//    }

    //    public HwUserToken getUserToken(String token) throws IllegalServiceException{
//        HwUserToken userToken = userTokenService.getByToken(token);
//        if (userToken == null){
//            throw new IllegalServiceException(ExceptionError.error_need_logined);
//        }
//
//        return userToken;
//    }

//    /**
//     * 写入用户日志
//     * @param userLog
//     */
//    public void saveUserLog(HwUserLog userLog){
//
//        /**
//         * and save user login logs.
//         */
//        publisher.publishEvent(new UserLogEvent(userLog));
//    }

}
