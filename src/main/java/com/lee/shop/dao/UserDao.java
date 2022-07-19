package com.lee.shop.dao;

import com.lee.shop.model.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getByEmail(String email);

    User getById(Long id);

    Long create(User user);

    void update(User user);
}
