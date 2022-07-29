package com.lee.shop.validator.logic;

import com.lee.shop.Constants;
import com.lee.shop.exception.InvalidUserInputException;
import com.lee.shop.model.dto.AddToShoppingCartDto;
import com.lee.shop.model.entity.Product;
import com.lee.shop.validator.LogicValidator;

import java.util.HashMap;
import java.util.Map;

public class AddToShoppingCartLogicValidator implements LogicValidator<AddToShoppingCartDto, Product> {

    private static final String PRODUCT = "PRODUCT";
    private static final String USER_ADD_TO_CART_JSP = "user/add-to-shopping-cart.jsp";

    @Override
    public void validate(AddToShoppingCartDto addToShoppingCartDto, Product product) {
        Map<String, String> errorsMap = new HashMap<>();
        validateProductCountIsPositive(addToShoppingCartDto, errorsMap);
        validateProductCountNotExceeded(addToShoppingCartDto, product, errorsMap);
        if (!errorsMap.isEmpty()) {
            Map<String, Object> models = new HashMap<>();
            models.put(Constants.DTO, addToShoppingCartDto);
            models.put(PRODUCT, product);
            throw new InvalidUserInputException(USER_ADD_TO_CART_JSP, errorsMap, models);
        }
    }

    private void validateProductCountIsPositive(
            AddToShoppingCartDto addToShoppingCartDto, Map<String, String> errorsMap) {
        if (addToShoppingCartDto.getCount() <= 0) {
            errorsMap.put("count", "action.count.add-to-card.not-positive");
        }
    }

    private void validateProductCountNotExceeded(
            AddToShoppingCartDto addToShoppingCartDto, Product product, Map<String, String> errorsMap) {
        if (addToShoppingCartDto.getCount() > product.getCount()) {
            errorsMap.put("count", "action.count.add-to-card.max.exceeded");
        }
    }
}
