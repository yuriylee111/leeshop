package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.ProductDao;
import com.lee.shop.model.entity.Product;
import com.lee.shop.model.form.AddToShoppingCartForm;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

public class ShowAddToShoppingCartFormAction implements Action {

    private static final String PRODUCT_ID = "productId";
    private static final String PRODUCT = "PRODUCT";
    private static final String USER_ADD_TO_CART_JSP = "user/add-to-shopping-cart.jsp";

    private final ProductDao productDao;

    public ShowAddToShoppingCartFormAction(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
        Product product = productDao.getById(productId);
        if (product != null) {
            request.setAttribute(PRODUCT, product);
            String backUrl = URLDecoder.decode(request.getParameter(Constants.BACK_URL), Constants.UTF_8);
            request.setAttribute(Constants.FORM, new AddToShoppingCartForm(productId, 1, backUrl));
            RoutingUtils.forwardToPage(USER_ADD_TO_CART_JSP, request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
