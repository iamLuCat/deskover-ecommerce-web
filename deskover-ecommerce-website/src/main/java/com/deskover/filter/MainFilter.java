package com.deskover.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class MainFilter extends OncePerRequestFilter {

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	logger.info(
    			request.getRequestURL()
				+ "?" + (request.getQueryString() == null ? "" : request.getQueryString())
				+ " - " + request.getMethod()
		);

        filterChain.doFilter(request, response);
    }

}
