package com.lee.shop.filter;

import com.lee.shop.Constants;
import com.lee.shop.exception.InvalidUserInputException;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class InvalidUserInputExceptionHandler implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(servletRequest, servletResponse);
        } catch (InvalidUserInputException exception) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            request.setAttribute(Constants.ERROR_MAP, exception.getErrorsMap());
            for (Map.Entry<String, Object> entry : exception.getModels().entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            RoutingUtils.forwardToPage(exception.getJspName(), request, response);
        }
    }
}
