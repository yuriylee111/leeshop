package com.lee.shop.service.impl;

import com.lee.shop.model.entity.Product;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.model.local.ShoppingCartItem;
import com.lee.shop.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.Iterator;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Override
    public void addProduct(ShoppingCart shoppingCart, Product product, int count) {
        for (ShoppingCartItem item : shoppingCart.getItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setCount(item.getCount() + count);
                refreshStatistics(shoppingCart);
                return;
            }
        }
        shoppingCart.getItems().add(new ShoppingCartItem(product, count));
        refreshStatistics(shoppingCart);
    }

    @Override
    public void removeProduct(ShoppingCart shoppingCart, Long productId, int count) {
        Iterator<ShoppingCartItem> iterator = shoppingCart.getItems().iterator();
        while (iterator.hasNext()) {
            ShoppingCartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                if (item.getCount() > count) {
                    item.setCount(item.getCount() - count);
                } else {
                    iterator.remove();
                }
                refreshStatistics(shoppingCart);
                return;
            }
        }
    }

    private void refreshStatistics(ShoppingCart shoppingCart) {
        int totalCount = 0;
        BigDecimal totalCost = BigDecimal.ZERO;
        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
            totalCount += shoppingCartItem.getCount();
            totalCost = totalCost.add(shoppingCartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCartItem.getCount())));
        }
        shoppingCart.setTotalCount(totalCount);
        shoppingCart.setTotalCost(totalCost);
    }
}
