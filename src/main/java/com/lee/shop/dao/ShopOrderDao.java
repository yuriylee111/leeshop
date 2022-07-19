package com.lee.shop.dao;

import com.lee.shop.model.entity.ShopOrder;

import java.util.List;

public interface ShopOrderDao {

    List<ShopOrder> getAll();

    List<ShopOrder> getAllForUser(Long userId);

    ShopOrder getById(Long id);

    Long create(ShopOrder shopOrder);

    void updateStatus(ShopOrder shopOrder);
}
