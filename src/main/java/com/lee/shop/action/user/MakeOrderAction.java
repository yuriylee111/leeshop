package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.ShopOrderDao;
import com.lee.shop.model.entity.ShopOrder;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.model.mapper.ShoppingCartToShopOrderMapper;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MakeOrderAction implements Action {

    private final ShoppingCartToShopOrderMapper mapper;
    private final ShopOrderDao shopOrderDao;

    public MakeOrderAction(ShoppingCartToShopOrderMapper mapper, ShopOrderDao shopOrderDao) {
        this.mapper = mapper;
        this.shopOrderDao = shopOrderDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart = WebUtils.getShoppingCart(request);
        if (!shoppingCart.isEmpty()) {
            User user = WebUtils.getCurrentSessionUser(request);
            ShopOrder shopOrder = mapper.map(user, shoppingCart);
            shopOrderDao.create(shopOrder);
        }
        WebUtils.releaseShoppingCart(request);
        RoutingUtils.redirect(Constants.Url.USER_MY_ORDERS, request, response);
    }
}
