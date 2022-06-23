package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.ProductDao;
import com.lee.shop.model.entity.Product;
import com.lee.shop.model.form.AddToShoppingCartForm;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;
import com.lee.shop.validator.form.AddToShoppingCartFormValidator;
import com.lee.shop.validator.logic.AddToShoppingCartLogicValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AddToShoppingCartAction implements Action {

    private static final String PRODUCT_ID = "productId";
    private static final String COUNT = "count";
    private static final String PRODUCT = "PRODUCT";
    private static final String USER_ADD_TO_CART_JSP = "user/add-to-shopping-cart.jsp";

    private final ProductDao productDao;
    private final AddToShoppingCartFormValidator addToShoppingCartFormValidator;
    private final AddToShoppingCartLogicValidator addToShoppingCartLogicValidator;

    public AddToShoppingCartAction(ProductDao productDao) {
        this.productDao = productDao;
        this.addToShoppingCartFormValidator = new AddToShoppingCartFormValidator();
        this.addToShoppingCartLogicValidator = new AddToShoppingCartLogicValidator();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddToShoppingCartForm form = new AddToShoppingCartForm(
                Integer.parseInt(request.getParameter(PRODUCT_ID)),
                Integer.parseInt(request.getParameter(COUNT)),
                request.getParameter(Constants.BACK_URL)
        );
        Product product = productDao.getById(form.getProductId());
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> validationErrors = addToShoppingCartFormValidator.getErrors(form);
        if (validationErrors.isEmpty()) {
            validationErrors = addToShoppingCartLogicValidator.getErrors(form, product);
            if (validationErrors.isEmpty()) {
                ShoppingCart shoppingCart = WebUtils.getShoppingCart(request);
                shoppingCart.addProduct(product, form.getCount());
                RoutingUtils.redirect(form.getBackUrl(), request, response);
                return;
            }
        }
        request.setAttribute(Constants.FORM, form);
        request.setAttribute(PRODUCT, product);
        request.setAttribute(Constants.ERROR_MAP, validationErrors);
        RoutingUtils.forwardToPage(USER_ADD_TO_CART_JSP, request, response);
    }
}
