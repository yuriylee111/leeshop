package com.lee.shop.filter;

import com.lee.shop.Constants;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (WebUtils.isCurrentSessionUserPresent(request)) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            RoutingUtils.redirect(Constants.Url.SIGN_IN, request, (HttpServletResponse) servletResponse);
        }
    }
}
