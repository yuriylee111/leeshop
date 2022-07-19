package com.lee.shop.model.entity;

import com.lee.shop.model.enumeration.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ShopOrder extends BaseEntity<Long> {

    private Long userId;
    private List<OrderDetail> items;
    private OrderStatus status;
    private Timestamp created;
    private BigDecimal totalCost;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderDetail> getItems() {
        return items;
    }

    public void setItems(List<OrderDetail> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "ShopOrder{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", items=" + items +
                ", status=" + status +
                ", created=" + created +
                ", totalCost=" + totalCost +
                "}";
    }
}
