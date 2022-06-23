package com.lee.shop.validator.logic;

import com.lee.shop.model.entity.Product;
import com.lee.shop.model.form.AddToShoppingCartForm;
import com.lee.shop.validator.LogicValidator;

import java.util.HashMap;
import java.util.Map;

public class AddToShoppingCartLogicValidator implements LogicValidator<AddToShoppingCartForm, Product> {

    @Override
    public Map<String, String> getErrors(AddToShoppingCartForm form, Product product) {
        Map<String, String> map = new HashMap<>();
        if (form.getCount() > product.getCount()) {
            map.put("count", "action.count.add-to-card.max.exceeded");
        }
        return map;
    }
}
