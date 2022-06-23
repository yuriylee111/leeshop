package com.lee.shop.action.common;

import com.lee.shop.action.Action;
import com.lee.shop.dao.ProductDao;
import com.lee.shop.model.entity.Product;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowProductsByCategoryAction implements Action {

    private static final String PRODUCTS = "PRODUCTS";
    private static final String PRODUCTS_JSP = "products.jsp";
    private static final String CATEGORY_ID = "categoryId";

    private final ProductDao productDao;

    public ShowProductsByCategoryAction(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        short categoryId = Short.parseShort(request.getParameter(CATEGORY_ID));
        List<Product> products = productDao.getAllForCategory(categoryId);
        request.setAttribute(PRODUCTS, products);
        RoutingUtils.forwardToPage(PRODUCTS_JSP, request, response);
    }
}
