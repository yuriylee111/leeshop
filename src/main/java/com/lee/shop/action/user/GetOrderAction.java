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

public class GetOrderAction implements Action {

    private static final String ORDER = "ORDER";
    private static final String USER_ORDER_JSP = "user/order.jsp";
    private static final String ID = "id";

    private final ShopOrderDao shopOrderDao;

    public GetOrderAction(ShopOrderDao shopOrderDao) {
        this.shopOrderDao = shopOrderDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter(ID));
        ShopOrder order = shopOrderDao.getById(id);
        if (order == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            User user = WebUtils.getCurrentSessionUser(request);
            if (order.getUserId().equals(user.getId())) {
                request.setAttribute(ORDER, order);
                RoutingUtils.forwardToPage(USER_ORDER_JSP, request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
