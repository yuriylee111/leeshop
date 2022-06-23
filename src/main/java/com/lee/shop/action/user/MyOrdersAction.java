package com.lee.shop.action.user;

import com.lee.shop.action.Action;
import com.lee.shop.dao.ShopOrderDao;
import com.lee.shop.model.entity.ShopOrder;
import com.lee.shop.model.entity.User;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MyOrdersAction implements Action {

    private static final String ORDERS = "ORDERS";
    private static final String USER_MY_ORDERS_JSP = "user/my-orders.jsp";

    private final ShopOrderDao shopOrderDao;

    public MyOrdersAction(ShopOrderDao shopOrderDao) {
        this.shopOrderDao = shopOrderDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = WebUtils.getCurrentSessionUser(request);
        List<ShopOrder> orders = shopOrderDao.getAllForUser(user.getId());
        request.setAttribute(ORDERS, orders);
        RoutingUtils.forwardToPage(USER_MY_ORDERS_JSP, request, response);
    }
}
