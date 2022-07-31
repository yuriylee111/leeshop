package com.lee.shop.filter;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(ErrorFilter.class);
    private static final String ERROR_DURING_PROCESSING_REQUEST_TEMPLATE = "Error during processing request: %s";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable throwable) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            LOGGER.error(String.format(ERROR_DURING_PROCESSING_REQUEST_TEMPLATE, request.getRequestURI()), throwable);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
