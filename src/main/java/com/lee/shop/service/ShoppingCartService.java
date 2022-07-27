package com.lee.shop.service;

import com.lee.shop.model.entity.Product;
import com.lee.shop.model.local.ShoppingCart;

public interface ShoppingCartService {

    void addProduct(ShoppingCart shoppingCart, Product product, int count);

    void removeProduct(ShoppingCart shoppingCart, Long productId, int count);
}
