package com.deskover.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.deskover.configuration.security.jwt.JwtAuthenticationEntryPoint;
import com.deskover.configuration.security.jwt.JwtRequestFilter;
import com.deskover.configuration.security.jwt.JwtUserDetailsService;
import com.deskover.util.JwtTokenUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSpringConfigured
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Configuration
	@Order(1)
	public static class AdminSecurityJWTConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private JwtRequestFilter jwtRequestFilter;

		@Autowired
		private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

		@Autowired
		private UserDetailsService jwtUserDetailsService;

		
		@Bean
		public JwtTokenUtil JwtTokenUtil() {
			return new JwtTokenUtil();
		}

		@Bean
		public JwtUserDetailsService JwtUserDetailsService() {
			return new JwtUserDetailsService();
		}

		@Bean
		public FilterRegistrationBean<JwtRequestFilter> jwtRequestFilterBean() {
			FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
			registrationBean.setFilter(jwtRequestFilter);
			registrationBean.addUrlPatterns("/v1/api/admin/*");
			return registrationBean;
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			// configure AuthenticationManager so that it knows from where to load
			// user for matching credentials
			// Use BCryptPasswordEncoder
			auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
		}

		protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
            .authorizeRequests()
            		.antMatchers("/v1/api/admin/auth/login").permitAll()
                	.antMatchers("/v1/api/admin/**", "/v1/api/admin").authenticated()
            .and().antMatcher("/v1/api/admin/**")
               		.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                	.exceptionHandling()
                	.defaultAuthenticationEntryPointFor(jwtAuthenticationEntryPoint,new AntPathRequestMatcher("/v1/api/admin/**"))
            .and().httpBasic();;
        }
	}

	@Configuration
	@Order(2)
	public static class CustomerWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Bean
		public WebUserDetailsService WebUserDetailsService() {
			return new WebUserDetailsService();
		}
		
		@Autowired
		public UserDetailsService webUserDetailsService;
		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			// configure AuthenticationManager so that it knows from where to load
			// user for matching credentials
			// Use BCryptPasswordEncoder
			auth.userDetailsService(webUserDetailsService).passwordEncoder(passwordEncoder());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/user").authenticated()
				.anyRequest().permitAll()
			.and().formLogin()
				.loginPage("/login")
		        .loginProcessingUrl("/user/login")
		        .defaultSuccessUrl("/index", true)
		        .failureUrl("/login?error=true")
		    .and().logout()
			    .logoutUrl("/user/logout")
			    .deleteCookies("JSESSIONID")
	        .and().sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	}

}
