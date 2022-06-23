package com.lee.shop.validator.form;

import com.lee.shop.model.form.AddToShoppingCartForm;
import com.lee.shop.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

public class AddToShoppingCartFormValidator implements FormValidator<AddToShoppingCartForm> {

    @Override
    public Map<String, String> getErrors(AddToShoppingCartForm addToShoppingCartForm) {
        Map<String, String> map = new HashMap<>();
        if (addToShoppingCartForm.getCount() <= 0) {
            map.put("count", "action.count.add-to-card.not-positive");
        }
        return map;
    }
}
