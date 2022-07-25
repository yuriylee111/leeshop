package com.lee.shop.validator.logic;

import com.lee.shop.model.dto.AddToShoppingCartDto;
import com.lee.shop.model.entity.Product;
import com.lee.shop.validator.LogicValidator;

import java.util.HashMap;
import java.util.Map;

public class AddToShoppingCartLogicValidator implements LogicValidator<AddToShoppingCartDto, Product> {

    @Override
    public Map<String, String> getErrors(AddToShoppingCartDto addToShoppingCartDto, Product product) {
        Map<String, String> map = new HashMap<>();
        if (addToShoppingCartDto.getCount() > product.getCount()) {
            map.put("count", "action.count.add-to-card.max.exceeded");
        }
        return map;
    }
}
