package com.deskover.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
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

import com.deskover.configuration.security.jwt.JwtAuthenticationEntryPoint;
import com.deskover.configuration.security.jwt.JwtRequestFilter;
import com.deskover.configuration.security.jwt.JwtUserDetailsService;
import com.deskover.util.JwtTokenUtil;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSpringConfigured
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
       
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    JwtTokenUtil JwtTokenUtil() {
        return new JwtTokenUtil();
    }
    
    @Bean
    JwtUserDetailsService JwtUserDetailsService() {
        return new JwtUserDetailsService();
    }
    
    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtRequestFilterBean() {
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtRequestFilter);
        registrationBean.addUrlPatterns("/v1/api/admin/*");
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
        	.antMatcher("/v1/api/admin/auth/login/**")
            .authorizeRequests()
            	.antMatchers("/v1/api/admin/auth/login/**")
            		.permitAll()
                .and()
            .antMatcher("/v1/api/admin/*")
            .authorizeRequests()
            	.antMatchers("/v1/api/admin/*")
            		.authenticated()
            		.and()
            	.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            	.exceptionHandling()
                	.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                	.and()
                .sessionManagement()
                	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
             	.authorizeRequests().anyRequest().permitAll();
    }

}
