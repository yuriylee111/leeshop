package com.lee.shop.action.common;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.Role;
import com.lee.shop.model.form.SignInForm;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;
import com.lee.shop.validator.form.SignInFormValidator;
import com.lee.shop.validator.logic.SignInLogicValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SignInAction implements Action {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String SIGN_IN_JSP = "sign-in.jsp";

    private final UserDao userDao;

    private final SignInFormValidator signInFormValidator;

    private final SignInLogicValidator signInLogicValidator;

    public SignInAction(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.signInFormValidator = new SignInFormValidator();
        this.signInLogicValidator = new SignInLogicValidator(passwordEncoder);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebUtils.isCurrentSessionUserPresent(request)) {
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            SignInForm form = new SignInForm(request.getParameter(EMAIL), request.getParameter(PASSWORD));
            Map<String, String> validationErrors = signInFormValidator.getErrors(form);
            if (validationErrors.isEmpty()) {
                User user = userDao.getByEmail(form.getEmail());
                validationErrors = signInLogicValidator.getErrors(form, user);
                if (validationErrors.isEmpty()) {
                    WebUtils.setCurrentSessionUser(request, user);
                    if (user.getRole() == Role.ADMIN) {
                        RoutingUtils.redirect(Constants.Url.ADMIN_SHOW_ALL_USERS, request, response);
                    } else {
                        RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
                    }
                    return;
                }
            }
            request.setAttribute(Constants.ERROR_MAP, validationErrors);
            RoutingUtils.forwardToPage(SIGN_IN_JSP, request, response);
        }
    }
}
