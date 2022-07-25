package com.lee.shop.validator.dto;

import com.lee.shop.model.dto.AddToShoppingCartDto;
import com.lee.shop.validator.DtoValidator;

import java.util.HashMap;
import java.util.Map;

public class AddToShoppingCartDtoValidator implements DtoValidator<AddToShoppingCartDto> {

    @Override
    public Map<String, String> getErrors(AddToShoppingCartDto addToShoppingCartDto) {
        Map<String, String> map = new HashMap<>();
        if (addToShoppingCartDto.getCount() <= 0) {
            map.put("count", "action.count.add-to-card.not-positive");
        }
        return map;
    }
}
