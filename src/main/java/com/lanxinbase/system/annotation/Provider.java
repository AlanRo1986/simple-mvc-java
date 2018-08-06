package com.lanxinbase.system.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by alan.luo on 2017/9/24.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Provider {
    String value() default "";
}
