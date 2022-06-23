package com.lee.shop.action.common;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowSignInFormAction implements Action {

    private static final String SIGN_IN_JSP = "sign-in.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebUtils.isCurrentSessionUserPresent(request)) {
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            RoutingUtils.forwardToPage(SIGN_IN_JSP, request, response);
        }
    }
}
