package com.lee.shop.action.admin;

import com.lee.shop.action.Action;
import com.lee.shop.dao.ShopOrderDao;
import com.lee.shop.model.entity.ShopOrder;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAllOrdersAction implements Action {

    private static final String ORDERS = "ORDERS";
    private static final String ADMIN_USERS_JSP = "admin/orders.jsp";

    private final ShopOrderDao shopOrderDao;

    public ShowAllOrdersAction(ShopOrderDao shopOrderDao) {
        this.shopOrderDao = shopOrderDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ShopOrder> orders = shopOrderDao.getAll();
        request.setAttribute(ORDERS, orders);
        RoutingUtils.forwardToPage(ADMIN_USERS_JSP, request, response);
    }
}
