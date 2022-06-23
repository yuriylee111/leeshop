package com.lee.shop.model.form;

public class AddToShoppingCartForm {

    private final int productId;
    private final int count;
    private final String backUrl;

    public AddToShoppingCartForm(int productId, int count, String backUrl) {
        this.productId = productId;
        this.count = count;
        this.backUrl = backUrl;
    }

    public int getProductId() {
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
        return "AddToShoppingCartForm{" +
                "productId=" + productId +
                ", count=" + count +
                ", backUrl='" + backUrl + '\'' +
                '}';
    }
}
