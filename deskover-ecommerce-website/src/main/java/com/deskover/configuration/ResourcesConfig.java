package com.deskover.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourcesConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(
				"/css/**",
				"/dist/**",
				"/fonts/**",
				"/img/**",
				"/js/**",
				"/vendor/**"
				).addResourceLocations(
				"classpath:/static/css/",
				"classpath:/static/dist/",
				"classpath:/static/fonts/",
				"classpath:/static/img/",
				"classpath:/static/js/",
				"classpath:/static/vendor/"
				);
	}
}
