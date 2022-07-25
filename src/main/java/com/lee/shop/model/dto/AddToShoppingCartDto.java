package com.lee.shop.model.dto;

public class AddToShoppingCartDto {

    private final long productId;
    private final int count;
    private final String backUrl;

    public AddToShoppingCartDto(long productId, int count, String backUrl) {
        this.productId = productId;
        this.count = count;
        this.backUrl = backUrl;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public String getBackUrl() {
        return backUrl;
    }

    @Override
    public String toString() {
        return "AddToShoppingCartDto{" +
                "productId=" + productId +
                ", count=" + count +
                ", backUrl='" + backUrl + '\'' +
                '}';
    }
}
