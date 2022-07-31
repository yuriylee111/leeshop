package com.lee.shop.util;

import com.lee.shop.Constants;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.local.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebUtils {

    private static final String GET = "GET";
    private static final String QUESTION_MARK = "?";

    public static String getCurrentGetUrl(HttpServletRequest request) {
        if (GET.equals(request.getMethod())) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                return request.getRequestURI() + QUESTION_MARK + queryString;
            } else {
                return request.getRequestURI();
            }
        } else {
            return null;
        }
    }

    public static boolean isCurrentSessionUserPresent(HttpServletRequest request) {
        return getCurrentSessionUser(request) != null;
    }

    public static User getCurrentSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(Constants.CURRENT_SESSION_USER);
    }

    public static void setCurrentSessionUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(Constants.CURRENT_SESSION_USER, user);
    }

    public static ShoppingCart getShoppingCart(HttpServletRequest request) {
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            request.getSession().setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
        }
        return shoppingCart;
    }

    public static void releaseShoppingCart(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.CURRENT_SHOPPING_CART);
    }

    public static void saveEncodedCurrentRequestUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String url = getCurrentGetUrl(request);
        if (url != null) {
            request.setAttribute(Constants.ENCODED_CURRENT_URL, URLEncoder.encode(url, Constants.UTF_8));
        }
    }
}
