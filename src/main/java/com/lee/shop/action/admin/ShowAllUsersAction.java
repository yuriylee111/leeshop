package com.lee.shop.action.admin;

import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.entity.User;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAllUsersAction implements Action {

    private static final String USERS = "USERS";
    private static final String ADMIN_USERS_JSP = "admin/users.jsp";

    private final UserDao userDao;

    public ShowAllUsersAction(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDao.getAll();
        request.setAttribute(USERS, users);
        RoutingUtils.forwardToPage(ADMIN_USERS_JSP, request, response);
    }
}
