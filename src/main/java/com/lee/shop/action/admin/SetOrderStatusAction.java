package com.lee.shop.action.admin;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.ShopOrderDao;
import com.lee.shop.exception.ApplicationException;
import com.lee.shop.model.entity.ShopOrder;
import com.lee.shop.model.enumeration.OrderStatus;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetOrderStatusAction implements Action {

    private static final String ID = "id";
    private static final String STATUS = "status";
    private static final String CAN_T_CHANGE_STATUS_EXCEPTION_TEMPLATE = "Can't change status from %s to %s";

    private final ShopOrderDao shopOrderDao;

    public SetOrderStatusAction(ShopOrderDao shopOrderDao) {
        this.shopOrderDao = shopOrderDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter(ID));
        OrderStatus status = OrderStatus.valueOf(request.getParameter(STATUS));
        ShopOrder order = shopOrderDao.getById(id);
        if (order == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            validateStatus(order.getStatus(), status);
            order.setStatus(status);
            shopOrderDao.updateStatus(order);
            RoutingUtils.redirect(Constants.Url.ADMIN_ALL_ORDERS, request, response);
        }
    }

    private void validateStatus(OrderStatus oldStatus, OrderStatus newStatus) {
        if (oldStatus == OrderStatus.CREATED) {
            if (newStatus == OrderStatus.ACCEPTED || newStatus == OrderStatus.CANCELLED) {
                return;
            }
        } else if (oldStatus == OrderStatus.ACCEPTED) {
            if (newStatus == OrderStatus.TRANSFERRED) {
                return;
            }
        }
        throw new ApplicationException(String.format(CAN_T_CHANGE_STATUS_EXCEPTION_TEMPLATE, oldStatus, newStatus));
    }
}
