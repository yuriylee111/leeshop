package com.lee.shop.model.entity;

public class OrderDetail extends BaseEntity<Long> {

    private Long orderId;
    private Product product;
    private int count;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + getId() +
                ", orderId=" + orderId +
                ", product=" + product +
                ", count=" + count +
                '}';
    }
}
