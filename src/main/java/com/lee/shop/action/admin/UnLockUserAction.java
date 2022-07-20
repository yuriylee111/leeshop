package com.lee.shop.action.admin;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.entity.User;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter(ID));
        User user = userDao.getById(id);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else {
            user.setActive(true);
            userDao.update(user);
        }
        RoutingUtils.redirect(Constants.Url.ADMIN_SHOW_ALL_USERS, request, response);
    }
}
