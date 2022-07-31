package com.lee.shop.dao.impl;

import com.lee.shop.dao.CategoryDao;
import com.lee.shop.exception.ApplicationException;
import com.lee.shop.jdbc.JdbcConnectionPool;
import com.lee.shop.model.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao {

    private final static String ALL_CATEGORIES = "SELECT * FROM category ORDER BY id";

    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";

    private static final String CAN_T_GET_ALL_CATEGORIES_TEMPLATE = "Can't get all categories: %s";

    public CategoryDaoImpl(JdbcConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public List<Category> getAll() {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ALL_CATEGORIES);
             ResultSet rs = statement.executeQuery()) {
            List<Category> categoryList = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong(CATEGORY_ID));
                category.setName(rs.getString(CATEGORY_NAME));
                categoryList.add(category);
            }
            return categoryList;
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(CAN_T_GET_ALL_CATEGORIES_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }
}
