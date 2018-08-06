package com.lanxinbase.config;

import com.lanxinbase.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by alan.luo on 2017/9/22.
 */
@Configuration
@Import(ThymeleafConfiguration.class)
public class ViewResolverConfiguration extends DelegatingWebMvcConfiguration {

    /**
     * 配置JSP视图解析器
     * @return
     */
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    /**
     * 配置静态资源处理
     * @param configurer
     */
    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //    @Bean
//    public RequestToViewNameTranslator requestToViewNameTranslator(){
//        DefaultRequestToViewNameTranslator viewNameTranslator = new DefaultRequestToViewNameTranslator();
//        viewNameTranslator.setPrefix("/WEB-INF/jsp/views/");
//        viewNameTranslator.setSuffix(".jsp");
//
//        return viewNameTranslator;
//    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
        super.configureViewResolvers(registry);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(Constant.uploadMaxSize);
        return resolver;
    }

}
