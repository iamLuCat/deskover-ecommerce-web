package com.deskover.vnpay;

import javax.servlet.http.HttpServlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
   @Bean    
   public ServletRegistrationBean<HttpServlet> stateServlet() {
       ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
       servRegBean.setServlet(new AjaxServlet());
       servRegBean.addUrlMappings("/vnpayajax/*");
       servRegBean.setLoadOnStartup(1);
       return servRegBean;
   }   


}  