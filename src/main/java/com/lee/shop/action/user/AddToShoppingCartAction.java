package com.lee.shop.action.user;

import com.lee.shop.Constants;
import com.lee.shop.action.Action;
import com.lee.shop.dao.ProductDao;
import com.lee.shop.model.dto.AddToShoppingCartDto;
import com.lee.shop.model.entity.Product;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.model.mapper.HttpServletRequestToAddToShoppingCartDtoMapper;
import com.lee.shop.service.ShoppingCartService;
import com.lee.shop.util.RoutingUtils;
import com.lee.shop.util.WebUtils;
import com.lee.shop.validator.dto.AddToShoppingCartDtoValidator;
import com.lee.shop.validator.logic.AddToShoppingCartLogicValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AddToShoppingCartAction implements Action {

    private static final String PRODUCT = "PRODUCT";
    private static final String USER_ADD_TO_CART_JSP = "user/add-to-shopping-cart.jsp";

    private final HttpServletRequestToAddToShoppingCartDtoMapper mapper;
    private final ProductDao productDao;
    private final AddToShoppingCartDtoValidator addToShoppingCartDtoValidator;
    private final AddToShoppingCartLogicValidator addToShoppingCartLogicValidator;
    private final ShoppingCartService shoppingCartService;

    public AddToShoppingCartAction(HttpServletRequestToAddToShoppingCartDtoMapper mapper,
                                   ProductDao productDao,
                                   ShoppingCartService shoppingCartService) {
        this.mapper = mapper;
        this.productDao = productDao;
        this.shoppingCartService = shoppingCartService;
        this.addToShoppingCartDtoValidator = new AddToShoppingCartDtoValidator();
        this.addToShoppingCartLogicValidator = new AddToShoppingCartLogicValidator();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddToShoppingCartDto addToShoppingCartDto = mapper.map(request);
        Product product = productDao.getById(addToShoppingCartDto.getProductId());
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> validationErrors = addToShoppingCartDtoValidator.getErrors(addToShoppingCartDto);
        if (validationErrors.isEmpty()) {
            validationErrors = addToShoppingCartLogicValidator.getErrors(addToShoppingCartDto, product);
            if (validationErrors.isEmpty()) {
                ShoppingCart shoppingCart = WebUtils.getShoppingCart(request);
                shoppingCartService.addProduct(shoppingCart, product, addToShoppingCartDto.getCount());
                RoutingUtils.redirect(addToShoppingCartDto.getBackUrl(), request, response);
                return;
            }
        }
        request.setAttribute(Constants.DTO, addToShoppingCartDto);
        request.setAttribute(PRODUCT, product);
        request.setAttribute(Constants.ERROR_MAP, validationErrors);
        RoutingUtils.forwardToPage(USER_ADD_TO_CART_JSP, request, response);
    }
}
