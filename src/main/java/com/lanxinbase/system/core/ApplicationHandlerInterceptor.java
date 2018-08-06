package com.lanxinbase.system.core;

import com.lanxinbase.system.exception.IllegalAccessDeniedException;
import com.lanxinbase.system.provider.SecurityProvider;
import com.lanxinbase.system.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * user access role check.
 * Created by alan.luo on 2017/10/6.
 */
public class ApplicationHandlerInterceptor  implements HandlerInterceptor {

    @Autowired
    private SecurityProvider security;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        try {
           return security.intersection();
        }catch (IllegalAccessDeniedException e){
            e.printStackTrace();

            ResultResp<Void> resp = new ResultResp<>();
            resp.setCode(e.getCode());
            resp.setInfo(e.getMessage());
            resp.setData(null);
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json");
            //httpServletResponse.setStatus(e.getCode());//打开此参数将使用restful风格返回状态码
            httpServletResponse.getWriter().append(JsonUtil.ObjectToJson(resp,true));
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {


    }
}
