package com.lanxinbase.system.basic;

import com.lanxinbase.system.exception.IllegalValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * Created by alan.luo on 2017/9/18.
 */
@RequestMapping(value = "/api")
@RestController
public abstract class BasicApiController extends AbstractCompactController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private HttpSession httpSession;

    public BasicApiController(){
        super(BasicApiController.class);
    }

    public BasicApiController(Class classz) {
        super(classz);
    }

    /**
     * 因为用户权限在拦截器中就已经拦截过，并且把比较有用的数据保存在session中
     * @return
     * @throws IllegalValidateException 如果没有数据则抛出异常请求登陆
     */
    public Integer getUserId() throws IllegalValidateException{
        Object userId = httpSession.getAttribute("userId");
        if (StringUtils.isEmpty(userId)){
            return 0;
        }
        return Integer.valueOf(userId.toString());
    }

//    /**
//     * 因为用户权限在拦截器中就已经拦截过，并且把比较有用的数据保存在session中
//     * @return
//     * @throws IllegalValidateException 如果没有数据则抛出异常请求登陆
//     */
//    public HwUserToken getUserToken() throws IllegalValidateException{
//        HwUserToken userToken = (HwUserToken) httpSession.getAttribute(HwUserToken.class.getName());
//        if (userToken == null){
//            throw new IllegalValidateException(ExceptionError.error_unauthorized_invalid);
//        }
//        return userToken;
//    }




}
