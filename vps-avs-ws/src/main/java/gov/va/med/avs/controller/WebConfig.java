package gov.va.med.avs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Tonté Pouncil on 2/10/15.
 */
@EnableWebMvc
@Configuration
@ComponentScan(value = {"gov.va.med.avs"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApplicationContext appContext;
    private HttpMessageConverter<?> mappingJackson2HttpMessageConverter;


    public WebConfig() {}

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getMarshallingHttpMessageConverter());
        converters.add(getMappingJackson2HttpMessageConverter());

        super.configureMessageConverters(converters);
    }

    public HttpMessageConverter<?> getMarshallingHttpMessageConverter() {
        return (MarshallingHttpMessageConverter) appContext.getBean("marshallingHttpMessageConverter");
    }

    public HttpMessageConverter<?> getMappingJackson2HttpMessageConverter() {
        return (MappingJackson2HttpMessageConverter) appContext.getBean("jackson2HttpMessageConverter");
    }
}
