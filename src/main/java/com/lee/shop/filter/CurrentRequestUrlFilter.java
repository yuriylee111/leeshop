package com.lee.shop.filter;

import com.lee.shop.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CurrentRequestUrlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        WebUtils.saveEncodedCurrentRequestUrl((HttpServletRequest) servletRequest);
        chain.doFilter(servletRequest, servletResponse);
    }
}
