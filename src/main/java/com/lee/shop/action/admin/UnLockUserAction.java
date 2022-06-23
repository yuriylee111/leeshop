package com.lee.shop.action.admin;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.Role;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnLockUserAction implements Action {

    private static final String ID = "id";
    private final UserDao userDao;

    public UnLockUserAction(UserDao userDao) {
        this.userDao = userDao;
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(ID));
        User user = userDao.getById(id);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else {
            user.setRole(Role.USER);
            userDao.update(user);
        }
        RoutingUtils.redirect(Constants.Url.ADMIN_SHOW_ALL_USERS, request, response);
    }
}
