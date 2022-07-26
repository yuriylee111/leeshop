package com.lee.shop.model.local;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingCart {

    private final List<ShoppingCartItem> items = new ArrayList<>();

    private int totalCount = 0;

    private BigDecimal totalCost = BigDecimal.ZERO;

    public Collection<ShoppingCartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return String.format("ShoppingCart [items=%s, totalCount=%s, totalCost=%s]", items, totalCount, totalCost);
    }
}
