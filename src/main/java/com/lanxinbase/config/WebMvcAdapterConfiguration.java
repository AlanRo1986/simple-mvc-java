package com.lanxinbase.config;

import com.lanxinbase.system.core.ApplicationHandlerInterceptor;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;


/**
 * Created by alan.luo on 2017/9/20.
 */
@Configuration
public class WebMvcAdapterConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("text","plain",Charset.forName("UTF-8")),
                new MediaType("text", "config",Charset.forName("UTF-8"))
        ));
        return stringHttpMessageConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application","json",Charset.forName("UTF-8")),
                new MediaType("text","json",Charset.forName("UTF-8"))
        ));

        /**
         * if set this code to valid,then if the response data is null,that cannot put out it.
         * example:
         *      {
         *          id:1,name:"name",data:null
         *      }
         *      putout:
         *      {
         *          id:1,name:"name"
         *      }
         */
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        return jackson2HttpMessageConverter;
    }

    @Bean
    public Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter() {
        Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();
        xmlConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "config", Charset.forName("UTF-8")),
                new MediaType("text", "config", Charset.forName("UTF-8"))
        ));
        return xmlConverter;
    }

    @Bean
    public MarshallingHttpMessageConverter marshallingHttpMessageConverter(){
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        xmlConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "config", Charset.forName("UTF-8")),
                new MediaType("text", "config",Charset.forName("UTF-8"))
        ));

//        Marshaller marshaller = new Marshaller() {
//            @Override
//            public boolean supports(Class<?> aClass) {
//                return false;
//            }
//
//            @Override
//            public void marshal(Object o, Result result) throws IOException, XmlMappingException {
//            }
//        };
//
//        Unmarshaller unmarshaller = new Unmarshaller() {
//            @Override
//            public boolean supports(Class<?> aClass) {
//                return false;
//            }
//
//            @Override
//            public Object unmarshal(Source source) throws IOException, XmlMappingException {
//                return null;
//            }
//        };
//        xmlConverter.setMarshaller(marshaller);
//        xmlConverter.setUnmarshaller(unmarshaller);
        return xmlConverter;
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter(){
        return new ByteArrayHttpMessageConverter();
    }

    @Bean
    public FormHttpMessageConverter formHttpMessageConverter(){
        return new FormHttpMessageConverter();
    }

    @Bean
    public SourceHttpMessageConverter sourceHttpMessageConverter(){
        return new SourceHttpMessageConverter();
    }

    @Bean
    public ApplicationHandlerInterceptor handlerInterceptor(){
        return new ApplicationHandlerInterceptor();
    }

    /**
     * add a http interceptor
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor()).addPathPatterns("/api/**","/admin/**","/store/**");
        super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(byteArrayHttpMessageConverter());
        converters.add(stringHttpMessageConverter());
        converters.add(formHttpMessageConverter());
        converters.add(sourceHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
        converters.add(marshallingHttpMessageConverter());
//        converters.add(jaxb2RootElementHttpMessageConverter());

        super.configureMessageConverters(converters);
    }

}
