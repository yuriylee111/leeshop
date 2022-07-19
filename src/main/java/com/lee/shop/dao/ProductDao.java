package com.lee.shop.dao;

import com.lee.shop.model.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    List<Product> getAllForCategory(Long categoryId);

    Product getById(Long id);
}
