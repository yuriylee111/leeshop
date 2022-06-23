package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowShoppingCartAction implements Action {

    private static final String USER_SHOPPING_CART_JSP = "user/shopping-cart.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart = WebUtils.getShoppingCart(request);
        if (shoppingCart.isEmpty()) {
            WebUtils.releaseShoppingCart(request);
            RoutingUtils.redirect(Constants.Url.SHOW_PRODUCTS, request, response);
        } else {
            RoutingUtils.forwardToPage(USER_SHOPPING_CART_JSP, request, response);
        }
    }
}
