package com.lee.shop.action.common;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.dto.SignInDto;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.Role;
import com.lee.shop.model.mapper.HttpServletRequestToSignInDtoMapper;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;
import com.lee.shop.validator.dto.SignInDtoValidator;
import com.lee.shop.validator.logic.SignInLogicValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SignInAction implements Action {

    private static final String SIGN_IN_JSP = "sign-in.jsp";

    private final HttpServletRequestToSignInDtoMapper mapper;

    private final UserDao userDao;

    private final SignInDtoValidator signInDtoValidator;

    private final SignInLogicValidator signInLogicValidator;

    public SignInAction(HttpServletRequestToSignInDtoMapper mapper, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.userDao = userDao;
        this.signInDtoValidator = new SignInDtoValidator();
        this.signInLogicValidator = new SignInLogicValidator(passwordEncoder);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebUtils.isCurrentSessionUserPresent(request)) {
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            SignInDto signInDto = mapper.map(request);
            Map<String, String> validationErrors = signInDtoValidator.getErrors(signInDto);
            if (validationErrors.isEmpty()) {
                User user = userDao.getByEmail(signInDto.getEmail());
                validationErrors = signInLogicValidator.getErrors(signInDto, user);
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
