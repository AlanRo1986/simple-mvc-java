package com.lanxinbase.system.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ServiceAspectInterceptor {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ServiceAspectInterceptor(){

    }

    @Pointcut("execution(public * com.lanxinbase.service.impl.*.getAll(..))")
    private void interceptorMethodGetAll(){

    }

    @Before("interceptorMethodGetAll()")
    public void before(){
        logger.info("Service getAll before..........................");
    }

    @After("interceptorMethodGetAll()")
    public void after(){
        logger.info("Service getAll after..........................");
    }

    @AfterReturning("execution(public * com.lanxinbase.repository.mybatis.mapper.*.getAll(..))")
    public void AfterReturning() {
        //logger.info("interceptor AfterReturning..........................");
    }
    @AfterThrowing("execution(public * com.lanxinbase.repository.mybatis.mapper.*.getAll(..))")
    public void AfterThrowing() {
        //logger.info("interceptor AfterThrowing..........................");
    }

    /**
     * 把修改的参数引入进来
     * @param id 参数id
     */
    @Pointcut("execution(public * com.lanxinbase.service.impl.*.getRowById(Integer))&&args(id)")
    private void interceptorMethodGetRowById(Integer id){

    }

    @Before("interceptorMethodGetRowById(id)")
    public void beforeGetRowById(Integer id){
        logger.info("Service getRowById before.........................."+id);

    }

    @After("interceptorMethodGetRowById(id)")
    public void afterGetRowById(Integer id){
        logger.info("Service getRowById after.........................."+id);

    }

}
