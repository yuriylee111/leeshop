package com.lee.shop.model.mapper;

import com.lee.shop.model.entity.OrderDetail;
import com.lee.shop.model.entity.ShopOrder;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.OrderStatus;
import com.lee.shop.model.local.ShoppingCart;
import com.lee.shop.model.local.ShoppingCartItem;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ShoppingCartToShopOrderMapper {

    public ShopOrder map(User user, ShoppingCart shoppingCart) {
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setStatus(OrderStatus.CREATED);
        shopOrder.setCreated(new Timestamp(System.currentTimeMillis()));
        shopOrder.setUserId(user.getId());
        shopOrder.setTotalCost(shoppingCart.getTotalCost());
        shopOrder.setItems(new ArrayList<>());
        for (ShoppingCartItem item : shoppingCart.getItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(item.getProduct());
            orderDetail.setCount(item.getCount());
            shopOrder.getItems().add(orderDetail);
        }
        return shopOrder;
    }
}
