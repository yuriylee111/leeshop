package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.form.UserForm;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;
import com.lee.shop.validator.form.UserFormValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UpdateMyAccountAction implements Action {

    private static final String USER_MY_ACCOUNT_JSP = "user/my-account.jsp";

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserFormValidator userFormValidator;

    public UpdateMyAccountAction(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userFormValidator = new UserFormValidator(userDao, false);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserForm form = new UserForm(request);
        Map<String, String> validationErrors = userFormValidator.getErrors(form);
        if (validationErrors.isEmpty()) {
            User user = userDao.getById(form.getId());
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            updateUserData(user, form);
            userDao.update(user);
            WebUtils.setCurrentSessionUser(request, user);
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            request.setAttribute(Constants.FORM, form);
            request.setAttribute(Constants.ERROR_MAP, validationErrors);
            RoutingUtils.forwardToPage(USER_MY_ACCOUNT_JSP, request, response);
        }
    }

    private void updateUserData(User user, UserForm form) {
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        if (form.isPasswordPresent()) {
            user.setPassword(passwordEncoder.encode(form.getPassword()));
        }
        user.setPhone(form.getPhone());
    }
}
