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

public class ShowProductsAction implements Action {

    private static final String PRODUCTS = "PRODUCTS";
    private static final String PRODUCTS_JSP = "products.jsp";

    private final ProductDao productDao;

    public ShowProductsAction(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDao.getAll();
        request.setAttribute(PRODUCTS, products);
        RoutingUtils.forwardToPage(PRODUCTS_JSP, request, response);
    }
}
