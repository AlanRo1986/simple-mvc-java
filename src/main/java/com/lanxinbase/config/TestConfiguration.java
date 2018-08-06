package com.lanxinbase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * 参数基于property文件形式保存的配置例子
 */
@Configuration
@PropertySource("classpath:property/test.properties")
@ImportResource("classpath:config/test-config.xml")
public class TestConfiguration {

    @Autowired
    private Environment env;

    public TestConfiguration() {
    }

    @Bean
    public String testConfigParamsOfProperty(){
        System.out.println(String.format("TestConfiguration:id=%s&name=%s&passwd=%s",env.getProperty("test.id"),env.getProperty("test.name"),env.getProperty("test.passwd")));
        return null;
    }

}
