package com.lee.shop.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class RoutingUtils {

    private static final String CURRENT_PAGE = "currentPage";
    private static final String PAGE_PREFIX = "page/";
    private static final String WEB_INF_JSP_PAGE_TEMPLATE_JSP = "/WEB-INF/JSP/page-template.jsp";

    public static void forwardToPage(String jspPage,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(CURRENT_PAGE, PAGE_PREFIX + jspPage);
        request.getRequestDispatcher(WEB_INF_JSP_PAGE_TEMPLATE_JSP).forward(request, response);
    }

    public static void redirect(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(url);
    }
}
