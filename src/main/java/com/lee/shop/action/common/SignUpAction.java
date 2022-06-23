package com.lee.shop.action.common;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.Role;
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

public class SignUpAction implements Action {

    private static final String SIGN_UP_JSP = "sign-up.jsp";

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserFormValidator userFormValidator;

    public SignUpAction(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userFormValidator = new UserFormValidator(userDao, true);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebUtils.isCurrentSessionUserPresent(request)) {
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            UserForm form = new UserForm(request);
            Map<String, String> validationErrors = userFormValidator.getErrors(form);
            if (validationErrors.isEmpty()) {
                User user = createFrom(form);
                Integer id = userDao.create(user);
                user.setId(id);
                WebUtils.setCurrentSessionUser(request, user);
                RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
            } else {
                request.setAttribute(Constants.FORM, form);
                request.setAttribute(Constants.ERROR_MAP, validationErrors);
                RoutingUtils.forwardToPage(SIGN_UP_JSP, request, response);
            }
        }
    }

    private User createFrom(UserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setPhone(form.getPhone());
        user.setRole(Role.USER);
        return user;
    }
}
