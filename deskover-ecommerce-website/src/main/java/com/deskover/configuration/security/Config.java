package com.deskover.configuration.security;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.deskover.configuration.security.entrypoint.ApiAuthenticationEntryPoint;
import com.deskover.other.util.JwtTokenUtil;
import com.deskover.service.filter.jwt.JwtApplicationFilter;
import com.deskover.service.filter.jwt.JwtDashboardFilter;
import com.deskover.service.jwt.AdminDetailsService;
import com.deskover.service.jwt.UsersDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSpringConfigured
public class Config extends WebSecurityConfigurerAdapter {

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
	public static class DashboardSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private JwtDashboardFilter jwtDashboardFilter;

		@Autowired
		private ApiAuthenticationEntryPoint jwtAuthenticationEntryPoint;

		@Autowired
		private AdminDetailsService adminDetailsService;

		@Bean
		public JwtTokenUtil jwtAdminTokenUtil() {
			return new JwtTokenUtil();
		}

		@Bean
		public AdminDetailsService jwtAdminDetailsService() {
			return new AdminDetailsService();
		}

		@Bean
		public FilterRegistrationBean<JwtDashboardFilter> jwtRequestFilterBean() {
			FilterRegistrationBean<JwtDashboardFilter> registrationBean = new FilterRegistrationBean<>();
			registrationBean.setFilter(jwtDashboardFilter);
			registrationBean.addUrlPatterns("/v1/api/admin/*");
			return registrationBean;
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder());
		}

		protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
            .authorizeRequests()
            		.antMatchers("/v1/api/admin/auth/login").permitAll()
                	.antMatchers("/v1/api/admin/**", "/v1/api/admin").authenticated()
            .and().antMatcher("/v1/api/admin/**")
               		.addFilterBefore(jwtDashboardFilter, UsernamePasswordAuthenticationFilter.class)
                	.exceptionHandling()
                	.defaultAuthenticationEntryPointFor(jwtAuthenticationEntryPoint,new AntPathRequestMatcher("/v1/api/admin/**"))
            .and().httpBasic()
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
	}

	@Configuration
	@Order(2)
	public static class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private JwtApplicationFilter jwtApplicationFilter;

		@Autowired
		private ApiAuthenticationEntryPoint jwtAuthenticationEntryPoint;

		@Autowired
		private UsersDetailsService usersDetailsService;

		@Bean
		public JwtTokenUtil jwtCustomerTokenUtil() {
			return new JwtTokenUtil();
		}

		@Bean
		public UsersDetailsService appUserDetailsService() {
			return new UsersDetailsService();
		}

		@Bean
		public FilterRegistrationBean<JwtApplicationFilter> jwtCustomerFilterBean() {
			FilterRegistrationBean<JwtApplicationFilter> registrationBean = new FilterRegistrationBean<>();
			registrationBean.setFilter(jwtApplicationFilter);
			registrationBean.addUrlPatterns("/v1/api/customer/*");
			return registrationBean;
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(usersDetailsService).passwordEncoder(passwordEncoder());
		}

		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
				.authorizeRequests()
					.antMatchers("/v1/api/customer/auth/login/**").permitAll()
					.antMatchers("/v1/api/customer/**", "/v1/api/customer").authenticated()
				.and().antMatcher("/v1/api/customer/**")
					.addFilterBefore(jwtApplicationFilter, UsernamePasswordAuthenticationFilter.class)
					.exceptionHandling()
					.defaultAuthenticationEntryPointFor(jwtAuthenticationEntryPoint,new AntPathRequestMatcher("/v1/api/customer/**"))
				.and().httpBasic()
				.and().sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
		}
	}
	
	@Configuration
	@Order(3)
	public static class CustomerWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Bean
		public UsersDetailsService UsersDetailsService() {
			return new UsersDetailsService();
		}
		
		@Autowired
		public UsersDetailsService usersDetailsService;
		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(usersDetailsService).passwordEncoder(passwordEncoder());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
			.authorizeRequests()
				.antMatchers("/account","/account/**").hasAnyRole("CUSTOMER")
				.anyRequest().permitAll()
			.and().formLogin()
				.loginPage("/login")
		        .loginProcessingUrl("/user/login")
		        .defaultSuccessUrl("/index", true)
		        .failureHandler(ecommerceAuthenticateFailureHandler())
		    .and().logout()
			    .logoutUrl("/user/logout")
			    .logoutSuccessHandler(ecommerceLogoutSuccessHandler())
			    .deleteCookies("JSESSIONID");
			
			http.oauth2Login()
				.loginPage("/auth/login/form")
				.defaultSuccessUrl("/oauth2/login/success", true)
				.failureUrl("/auth/login/error")
				.authorizationEndpoint()
				.baseUri("/oauth2/authorization");
			
		}
		
		private AuthenticationFailureHandler ecommerceAuthenticateFailureHandler() {
			return (request, response, authentication) -> {
				request.getSession().setAttribute("error", "Tài khoản hoặc mật khẩu không đúng");
				response.sendRedirect("/login");
			};
		}
		
		private LogoutSuccessHandler ecommerceLogoutSuccessHandler() {
			return (request, response, authentication) -> {
				request.getSession().setAttribute("message", "Bạn đã đăng xuất khỏi tài khoản");
				response.sendRedirect("/login");
			};
		}
	}

}
