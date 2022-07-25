package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.ProductDao;
import com.lee.shop.model.entity.Product;
import com.lee.shop.model.dto.AddToShoppingCartDto;
import com.lee.shop.model.mapper.HttpServletRequestToAddToShoppingCartDtoMapper;
import com.lee.shop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAddToShoppingCartDtoAction implements Action {

    private static final String PRODUCT_ID = "productId";
    private static final String PRODUCT = "PRODUCT";
    private static final String USER_ADD_TO_CART_JSP = "user/add-to-shopping-cart.jsp";

    private final HttpServletRequestToAddToShoppingCartDtoMapper mapper;
    private final ProductDao productDao;

    public ShowAddToShoppingCartDtoAction(HttpServletRequestToAddToShoppingCartDtoMapper mapper, ProductDao productDao) {
        this.mapper = mapper;
        this.productDao = productDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
        Product product = productDao.getById(productId);
        if (product != null) {
            request.setAttribute(PRODUCT, product);
            AddToShoppingCartDto addToShoppingCartDto = mapper.map(productId, request, 1);
            request.setAttribute(Constants.DTO, addToShoppingCartDto);
            RoutingUtils.forwardToPage(USER_ADD_TO_CART_JSP, request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
