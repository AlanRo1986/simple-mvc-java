package com.lanxinbase.system.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by alan.luo on 2017/10/9.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Aspect
@Component
public @interface Interceptor {
    String value() default "";
}


