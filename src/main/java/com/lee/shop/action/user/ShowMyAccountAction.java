package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.model.dto.UserDto;
import com.lee.shop.model.mapper.UserToUserDtoMapper;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowMyAccountAction implements Action {

    private static final String USER_MY_ACCOUNT_JSP = "user/my-account.jsp";

    private final UserToUserDtoMapper userToUserDtoMapper;

    public ShowMyAccountAction(UserToUserDtoMapper userToUserDtoMapper) {
        this.userToUserDtoMapper = userToUserDtoMapper;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDto userDto = userToUserDtoMapper.map(WebUtils.getCurrentSessionUser(request));
        request.setAttribute(Constants.DTO, userDto);
        RoutingUtils.forwardToPage(USER_MY_ACCOUNT_JSP, request, response);
    }
}
