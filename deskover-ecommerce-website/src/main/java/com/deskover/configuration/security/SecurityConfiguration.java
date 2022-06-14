package com.deskover.configuration.security;

import com.deskover.configuration.security.admin.AdminDetailsService;
import com.deskover.configuration.security.jwt.AuthEntryPointJwt;
import com.deskover.configuration.security.jwt.AuthAdminTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public AuthAdminTokenFilter authenticationJwtTokenFilter() {
            return new AuthAdminTokenFilter();
        }

        @Bean
        public UserDetailsService adminDetailService() {
            return new AdminDetailsService();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

            authProvider.setUserDetailsService(adminDetailService());
            authProvider.setPasswordEncoder(passwordEncoder());

            return authProvider;
        }

        public AdminSecurityConfiguration() {
            super();
        }

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable();

            http.authorizeRequests()
                    .antMatchers("/v1/api/admin/auth/**").permitAll()
                    .anyRequest().authenticated();
            http.httpBasic();
            http.exceptionHandling()
                    .accessDeniedPage("/error/forbidden")
                    .authenticationEntryPoint(unauthorizedHandler);
            http.logout()
                    .deleteCookies("JSESSIONID")
                    .permitAll();
            http.rememberMe()
                    .key("uniqueAndSecret")
                    .userDetailsService(adminDetailService())
                    .tokenValiditySeconds(24 * 60 * 60);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
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
