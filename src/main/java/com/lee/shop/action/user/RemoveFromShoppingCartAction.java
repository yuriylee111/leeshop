package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.service.ShoppingCartService;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveFromShoppingCartAction implements Action {

    private static final String PRODUCT_ID = "productId";
    private static final String COUNT = "count";

    private final ShoppingCartService shoppingCartService;

    public RemoveFromShoppingCartAction(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart = WebUtils.getShoppingCart(request);
        Long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
        int count = Integer.parseInt(request.getParameter(COUNT));
        shoppingCartService.removeProduct(shoppingCart, productId,count);
        if (shoppingCart.isEmpty()) {
            WebUtils.releaseShoppingCart(request);
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            RoutingUtils.redirect(Constants.Url.USER_SHOPPING_CART, request, response);
        }
    }
}
