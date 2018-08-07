package com.lanxinbase.app.api;

import com.lanxinbase.system.core.ResultResp;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@ControllerAdvice
public class ErrorController {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResp<Void> error(HttpServletRequest request, HttpServletResponse response,Exception e){
        e.printStackTrace();

        return new ResultResp<>(0,e.getMessage());
    }
}
