package com.lee.shop.action.common;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.dto.UserDto;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.mapper.HttpServletRequestToUserDtoMapper;
import com.lee.shop.model.mapper.UserDtoToUserMapper;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;
import com.lee.shop.validator.dto.UserDtoValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpAction implements Action {

    private static final String SIGN_UP_JSP = "sign-up.jsp";

    private final HttpServletRequestToUserDtoMapper httpServletRequestToUserDtoMapper;
    private final UserDtoToUserMapper userDtoToUserMapper;
    private final UserDao userDao;
    private final UserDtoValidator userDtoValidator;

    public SignUpAction(HttpServletRequestToUserDtoMapper httpServletRequestToUserDtoMapper,
                        UserDtoToUserMapper userDtoToUserMapper,
                        UserDao userDao) {
        this.httpServletRequestToUserDtoMapper = httpServletRequestToUserDtoMapper;
        this.userDtoToUserMapper = userDtoToUserMapper;
        this.userDao = userDao;
        this.userDtoValidator = new UserDtoValidator(userDao, true, SIGN_UP_JSP);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebUtils.isCurrentSessionUserPresent(request)) {
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            UserDto userDto = httpServletRequestToUserDtoMapper.map(request);
            userDtoValidator.validate(userDto);

            User user = userDtoToUserMapper.map(userDto);
            Long id = userDao.create(user);
            user.setId(id);
            WebUtils.setCurrentSessionUser(request, user);
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        }
    }
}
