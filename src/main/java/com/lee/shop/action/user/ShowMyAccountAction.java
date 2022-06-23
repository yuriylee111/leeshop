package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.model.form.UserForm;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowMyAccountAction implements Action {

    private static final String USER_MY_ACCOUNT_JSP = "user/my-account.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Constants.FORM, new UserForm(WebUtils.getCurrentSessionUser(request)));
        RoutingUtils.forwardToPage(USER_MY_ACCOUNT_JSP, request, response);
    }
}
