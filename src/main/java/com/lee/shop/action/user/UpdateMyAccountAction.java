package com.lee.shop.action.user;

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

public class UpdateMyAccountAction implements Action {

    private static final String USER_MY_ACCOUNT_JSP = "user/my-account.jsp";

    private final HttpServletRequestToUserDtoMapper httpServletRequestToUserDtoMapper;
    private final UserDtoToUserMapper userDtoToUserMapper;
    private final UserDao userDao;
    private final UserDtoValidator userDtoValidator;

    public UpdateMyAccountAction(HttpServletRequestToUserDtoMapper httpServletRequestToUserDtoMapper,
                                 UserDtoToUserMapper userDtoToUserMapper,
                                 UserDao userDao) {
        this.httpServletRequestToUserDtoMapper = httpServletRequestToUserDtoMapper;
        this.userDtoToUserMapper = userDtoToUserMapper;
        this.userDao = userDao;
        this.userDtoValidator = new UserDtoValidator(userDao, false, USER_MY_ACCOUNT_JSP);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDto userDto = httpServletRequestToUserDtoMapper.map(request);
        userDtoValidator.validate(userDto);

        User user = userDao.getById(userDto.getId());
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        user = userDtoToUserMapper.map(user, userDto);
        userDao.update(user);
        WebUtils.setCurrentSessionUser(request, user);
        RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
    }
}
