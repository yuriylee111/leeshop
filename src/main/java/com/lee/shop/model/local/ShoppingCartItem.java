package com.lee.shop.model.local;

import com.lee.shop.model.entity.Product;

public class ShoppingCartItem {
    private final Product product;
    private int count;

    public ShoppingCartItem(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("ShoppingCartItem [product=%s, count=%s]", product, count);
    }
}
