package com.yf;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public HttpMessageConverter<String> responseBodyConverter(){
        return  new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
        addDefaultHttpMessageConverters(converters);
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
       configurer.favorPathExtension(false);

    }
}
