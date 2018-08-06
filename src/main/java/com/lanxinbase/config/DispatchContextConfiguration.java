package com.lanxinbase.config;

import com.lanxinbase.system.annotation.EventBus;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;


/**
 * Created by alan.luo on 2017/9/18.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.lanxinbase")
//@Import({WebMvcAdapterConfiguration.class,MybatisConfiguration.class,ViewResolverConfiguration.class})
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@EnableAspectJAutoProxy
public class DispatchContextConfiguration {

    public DispatchContextConfiguration(){

    }



}
