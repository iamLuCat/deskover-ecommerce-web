package com.deskover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DeskoverEcommerceWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskoverEcommerceWebsiteApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	 }

}
