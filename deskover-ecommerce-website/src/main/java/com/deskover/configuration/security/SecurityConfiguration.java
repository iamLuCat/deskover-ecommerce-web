package com.deskover.configuration.security;

import com.deskover.configuration.security.jwt.AuthEntryPointJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Configuration
    @Order(1)
    public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
        public AdminSecurityConfiguration() {
            super();
        }

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable();

            http.authorizeRequests()
                    .antMatchers("/v1/api/admin/auth/**").permitAll()
                    .anyRequest().authenticated();

            http.httpBasic();

            http.logout().deleteCookies("JSESSIONID");

            http.rememberMe()
                    .key("uniqueAndSecret")
                    .tokenValiditySeconds(24 * 60 * 60);
        }
    }

    @Configuration
    @Order(2)
    public class CustomerSecurityConfiguration extends WebSecurityConfigurerAdapter {
        public CustomerSecurityConfiguration() {
            super();
        }

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/v1/api/customer/login").permitAll()
                    .anyRequest().authenticated();

            http.httpBasic();

            http.logout().deleteCookies("JSESSIONID");

            http.rememberMe()
                    .key("uniqueAndSecret")
                    .tokenValiditySeconds(24 * 60 * 60);

        }
    }

}
