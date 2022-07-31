package com.lee.shop.dao.impl;

import com.lee.shop.dao.ProductDao;
import com.lee.shop.exception.ApplicationException;
import com.lee.shop.jdbc.JdbcConnectionPool;
import com.lee.shop.model.entity.Category;
import com.lee.shop.model.entity.Country;
import com.lee.shop.model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

    private static final String All_PRODUCTS = "SELECT * FROM product " +
            "JOIN category ON product.category_id = category.id " +
            "JOIN country ON product.country_id = country.id " +
            "ORDER BY product.id";

    private static final String All_PRODUCTS_BY_CATEGORY = "SELECT * FROM product " +
            "JOIN category ON product.category_id = category.id " +
            "JOIN country ON product.country_id = country.id " +
            "WHERE product.category_id = ? ORDER BY product.id";

    private static final String PRODUCT_BY_ID = "SELECT * FROM product " +
            "JOIN category ON product.category_id = category.id " +
            "JOIN country ON product.country_id = country.id " +
            "WHERE product.id = ? ORDER BY product.id";

    private static final String PRODUCT_ID = "product.id";
    private static final String PRODUCT_NAME = "product.name";
    private static final String PRODUCT_DESCRIPTION = "product.description";
    private static final String PRODUCT_IMAGE = "product.image";
    private static final String PRODUCT_PRICE = "product.price";
    private static final String PRODUCT_QUANTITY = "product.count";

    private static final String COUNTRY_ID = "country.id";
    private static final String COUNTRY_NAME = "country.name";
    private static final String CATEGORY_ID = "category.id";
    private static final String CATEGORY_NAME = "category.name";

    private static final String CAN_T_GET_ALL_PRODUCTS_TEMPLATE = "Can't get all products: %s";
    private static final String CAN_T_GET_PRODUCTS_BY_CATEGORY_TEMPLATE = "Can't get products by category = %s: %s";
    private static final String CAN_T_GET_PRODUCT_BY_ID_TEMPLATE = "Can't get product by id = %s: %s";

    public ProductDaoImpl(JdbcConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public List<Product> getAll() {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(All_PRODUCTS)) {
            try (ResultSet rs = statement.executeQuery()) {
                List<Product> productList = new ArrayList<>();
                while (rs.next()) {
                    productList.add(convertResultSetToProduct(rs));
                }
                return productList;
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(CAN_T_GET_ALL_PRODUCTS_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public List<Product> getAllForCategory(Long categoryId) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(All_PRODUCTS_BY_CATEGORY)) {
            statement.setLong(1, categoryId);
            try (ResultSet rs = statement.executeQuery()) {
                List<Product> productList = new ArrayList<>();
                while (rs.next()) {
                    productList.add(convertResultSetToProduct(rs));
                }
                return productList;
            }
        } catch (SQLException exception) {
            throw new ApplicationException(
                    String.format(CAN_T_GET_PRODUCTS_BY_CATEGORY_TEMPLATE, categoryId, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public Product getById(Long id) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(PRODUCT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return convertResultSetToProduct(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(CAN_T_GET_PRODUCT_BY_ID_TEMPLATE, id, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    private Product convertResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong(PRODUCT_ID));
        product.setName(rs.getString(PRODUCT_NAME));
        product.setDescription(rs.getString(PRODUCT_DESCRIPTION));
        product.setImage(rs.getString(PRODUCT_IMAGE));
        product.setPrice(rs.getBigDecimal(PRODUCT_PRICE));
        product.setCount(rs.getInt(PRODUCT_QUANTITY));
        Country country = new Country(rs.getLong(COUNTRY_ID), rs.getString(COUNTRY_NAME));
        Category category = new Category(rs.getLong(CATEGORY_ID), rs.getString(CATEGORY_NAME));
        product.setCountry(country);
        product.setCategory(category);
        return product;
    }
}
