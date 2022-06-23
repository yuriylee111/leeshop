package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.ShopOrderDao;
import com.lee.shop.model.entity.OrderDetail;
import com.lee.shop.model.entity.ShopOrder;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.OrderStatus;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.model.local.ShoppingCartItem;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MakeOrderAction implements Action {

    private final ShopOrderDao shopOrderDao;

    public MakeOrderAction(ShopOrderDao shopOrderDao) {
        this.shopOrderDao = shopOrderDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart = WebUtils.getShoppingCart(request);
        if (!shoppingCart.isEmpty()) {
            User user = WebUtils.getCurrentSessionUser(request);
            ShopOrder shopOrder = createShopOrder(user, shoppingCart);
            shopOrderDao.create(shopOrder);
        }
        WebUtils.releaseShoppingCart(request);
        RoutingUtils.redirect(Constants.Url.USER_MY_ORDERS, request, response);
    }

    private ShopOrder createShopOrder(User user, ShoppingCart shoppingCart) {
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setStatus(OrderStatus.CREATED);
        shopOrder.setCreated(new Timestamp(System.currentTimeMillis()));
        shopOrder.setUserId(user.getId());
        shopOrder.setTotalCost(shoppingCart.getTotalCost());
        shopOrder.setItems(new ArrayList<>());
        for (ShoppingCartItem item : shoppingCart.getItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(item.getProduct());
            orderDetail.setCount(item.getCount());
            shopOrder.getItems().add(orderDetail);
        }
        return shopOrder;
    }
}
