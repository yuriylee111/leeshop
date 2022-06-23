package com.lee.shop.model.local;

import com.lee.shop.model.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {

    private final List<ShoppingCartItem> items = new ArrayList<>();

    private int totalCount = 0;

    private BigDecimal totalCost = BigDecimal.ZERO;

    public void addProduct(Product product, int count) {
        for (ShoppingCartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setCount(item.getCount() + count);
                refreshStatistics();
                return;
            }
        }
        items.add(new ShoppingCartItem(product, count));
        refreshStatistics();
    }

    public void removeProduct(Integer productId, int count) {
        Iterator<ShoppingCartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            ShoppingCartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                if (item.getCount() > count) {
                    item.setCount(item.getCount() - count);
                } else {
                    iterator.remove();
                }
                refreshStatistics();
                return;
            }
        }
    }

    public Collection<ShoppingCartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    private void refreshStatistics() {
        totalCount = 0;
        totalCost = BigDecimal.ZERO;
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            totalCount += shoppingCartItem.getCount();
            totalCost = totalCost.add(shoppingCartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCartItem.getCount())));
        }
    }

    @Override
    public String toString() {
        return String.format("ShoppingCart [items=%s, totalCount=%s, totalCost=%s]", items, totalCount, totalCost);
    }
}
