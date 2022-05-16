package com.soap.webservices.example.soap;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.security.auth.callback.CallbackHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


//Enable spring web services
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    private Map<String,String> userMap = Collections.singletonMap("user1","password1");

    //Message Dispatcher Servlet
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context){
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
    }

    @Bean(name="courses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CoursesPort");
        definition.setTargetNamespace("http://soap.webservices.example/courses");
        definition.setLocationUri("/ws");
        definition.setSchema(coursesSchema);
        return definition;
    }

    @Bean
    public XsdSchema coursesSchema(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/course-details.xsd"));
    }


    @Bean
    public XwsSecurityInterceptor securityInterceptor(){
        XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
        //Callback Handler
        securityInterceptor.setCallbackHandler(callbackHandler());
        //Security Policy
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return securityInterceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler(){
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        handler.setUsersMap(userMap);
        return handler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors){
        interceptors.add(securityInterceptor());
    }

}
