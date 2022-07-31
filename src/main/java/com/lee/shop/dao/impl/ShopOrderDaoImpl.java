package com.lee.shop.dao.impl;

import com.lee.shop.dao.ShopOrderDao;
import com.lee.shop.exception.ApplicationException;
import com.lee.shop.jdbc.JdbcConnectionPool;
import com.lee.shop.model.entity.*;
import com.lee.shop.model.enumeration.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShopOrderDaoImpl extends BaseDaoImpl implements ShopOrderDao {

    private static final String All_ORDERS =
            "SELECT * FROM shop_order " +
                    "JOIN order_detail ON order_detail.order_id = shop_order.id " +
                    "JOIN product ON order_detail.product_id=product.id " +
                    "JOIN category ON product.category_id = category.id " +
                    "JOIN country ON product.country_id = country.id " +
                    "ORDER BY shop_order.id DESC, order_detail.id";

    private static final String All_ORDERS_BY_USER_ID =
            "SELECT * FROM shop_order " +
                    "JOIN order_detail ON order_detail.order_id = shop_order.id " +
                    "JOIN product ON order_detail.product_id=product.id " +
                    "JOIN category ON product.category_id = category.id " +
                    "JOIN country ON product.country_id = country.id " +
                    "WHERE shop_order.user_id = ? ORDER BY shop_order.id DESC, order_detail.id";

    private static final String ORDER_BY_ID =
            "SELECT * FROM shop_order " +
                    "JOIN order_detail ON order_detail.order_id = shop_order.id " +
                    "JOIN product ON order_detail.product_id=product.id " +
                    "JOIN category ON product.category_id = category.id " +
                    "JOIN country ON product.country_id = country.id " +
                    "WHERE order_detail.order_id = ? ORDER BY order_detail.id";

    private static final String NEW_ORDER = "INSERT INTO shop_order(user_id, status, created, total_cost) VALUES (?, ?, ?, ?)";
    private static final String NEW_ORDER_DETAIL = "INSERT INTO order_detail(order_id, product_id, count) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER = "UPDATE shop_order SET status=? WHERE id=?";

    private static final String ORDER_DETAIL_ID = "order_detail.id";
    private static final String ORDER_DETAIL_COUNT = "order_detail.count";

    private static final String ORDER_ID = "shop_order.id";
    private static final String ORDER_USER_ID = "shop_order.user_id";
    private static final String ORDER_STATUS = "shop_order.status";
    private static final String ORDER_TOTAL_COST = "shop_order.total_cost";
    private static final String ORDER_CREATED = "shop_order.created";

    private static final String PRODUCT_ID = "product.id";
    private static final String PRODUCT_NAME = "product.name";
    private static final String PRODUCT_DESCRIPTION = "product.description";
    private static final String PRODUCT_IMAGE = "product.image";
    private static final String PRODUCT_PRICE = "product.price";
    private static final String PRODUCT_COUNT = "product.count";

    private static final String COUNTRY_ID = "country.id";
    private static final String COUNTRY_NAME = "country.name";

    private static final String CATEGORY_ID = "category.id";
    private static final String CATEGORY_NAME = "category.name";

    private static final String CAN_T_GET_ALL_ORDERS_TEMPLATE = "Can't get all orders: %s";
    private static final String CAN_T_GET_ORDERS_FOR_USER_TEMPLATE = "Can't get orders for user = %s: %s";
    private static final String CAN_T_GET_ORDER_BY_ID_TEMPLATE = "Can't get order by id = %s: %s";
    private static final String CAN_T_READ_GENERATED_KEYS_FOR_TEMPLATE = "Can't read generated keys for: %s";
    private static final String FAILED_TO_ADD_ORDER_TEMPLATE = "Failed to add order: %s";
    private static final String NOTHING_TO_UPDATE_BECAUSE_ORDER_NOT_FOUND_BY_ID_TEMPLATE =
            "Nothing to update, because order not found by id: %s";
    private static final String FAILED_TO_UPDATE_ORDER_TEMPLATE = "Failed to update order: %s";

    public ShopOrderDaoImpl(JdbcConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public List<ShopOrder> getAll() {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(All_ORDERS)) {
            try (ResultSet rs = statement.executeQuery()) {
                Map<Long, ShopOrder> shopOrderMap = new LinkedHashMap<>();
                while (rs.next()) {
                    convertResultSetToShopOrder(rs, shopOrderMap);
                }
                return new ArrayList<>(shopOrderMap.values());
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(CAN_T_GET_ALL_ORDERS_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public List<ShopOrder> getAllForUser(Long userId) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(All_ORDERS_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                Map<Long, ShopOrder> shopOrderMap = new LinkedHashMap<>();
                while (rs.next()) {
                    convertResultSetToShopOrder(rs, shopOrderMap);
                }
                return new ArrayList<>(shopOrderMap.values());
            }
        } catch (SQLException exception) {
            throw new ApplicationException(
                    String.format(CAN_T_GET_ORDERS_FOR_USER_TEMPLATE, userId, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public ShopOrder getById(Long id) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ORDER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                Map<Long, ShopOrder> shopOrderMap = new LinkedHashMap<>();
                while (rs.next()) {
                    convertResultSetToShopOrder(rs, shopOrderMap);
                }
                return shopOrderMap.isEmpty() ? null : shopOrderMap.values().iterator().next();
            }
        } catch (SQLException exception) {
            throw new ApplicationException(
                    String.format(CAN_T_GET_ORDER_BY_ID_TEMPLATE, id, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    private void convertResultSetToShopOrder(ResultSet rs, Map<Long, ShopOrder> shopOrderMap) throws SQLException {
        long orderId = rs.getLong(ORDER_ID);
        ShopOrder shopOrder = shopOrderMap.get(orderId);
        if (shopOrder == null) {
            shopOrder = new ShopOrder();
            shopOrder.setId(rs.getLong(ORDER_ID));
            shopOrder.setUserId(rs.getLong(ORDER_USER_ID));
            shopOrder.setStatus(OrderStatus.valueOf(rs.getString(ORDER_STATUS)));
            shopOrder.setCreated(rs.getTimestamp(ORDER_CREATED));
            shopOrder.setItems(new ArrayList<>());
            shopOrder.setTotalCost(rs.getBigDecimal(ORDER_TOTAL_COST));
            shopOrderMap.put(orderId, shopOrder);
        }
        Product product = new Product();
        product.setId(rs.getLong(PRODUCT_ID));
        product.setName(rs.getString(PRODUCT_NAME));
        product.setDescription(rs.getString(PRODUCT_DESCRIPTION));
        product.setImage(rs.getString(PRODUCT_IMAGE));
        product.setPrice(rs.getBigDecimal(PRODUCT_PRICE));
        product.setCount(rs.getInt(PRODUCT_COUNT));
        Country country = new Country(rs.getLong(COUNTRY_ID), rs.getString(COUNTRY_NAME));
        Category category = new Category(rs.getLong(CATEGORY_ID), rs.getString(CATEGORY_NAME));
        product.setCountry(country);
        product.setCategory(category);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(rs.getLong(ORDER_DETAIL_ID));
        orderDetail.setOrderId(orderId);
        orderDetail.setProduct(product);
        orderDetail.setCount(rs.getInt(ORDER_DETAIL_COUNT));

        shopOrder.getItems().add(orderDetail);
    }

    @Override
    public Long create(ShopOrder shopOrder) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try {
            try (PreparedStatement shopOrderStatement = connection.prepareStatement(NEW_ORDER, new String[]{ORDER_ID})) {
                shopOrderStatement.setLong(1, shopOrder.getUserId());
                shopOrderStatement.setString(2, shopOrder.getStatus().name());
                shopOrderStatement.setTimestamp(3, shopOrder.getCreated());
                shopOrderStatement.setBigDecimal(4, shopOrder.getTotalCost());
                shopOrderStatement.executeUpdate();

                try (ResultSet generatedKeys = shopOrderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long orderId = generatedKeys.getLong(1);
                        try (PreparedStatement orderDetailStatement = connection.prepareStatement(NEW_ORDER_DETAIL)) {
                            for (OrderDetail item : shopOrder.getItems()) {
                                orderDetailStatement.setLong(1, orderId);
                                orderDetailStatement.setLong(2, item.getProduct().getId());
                                orderDetailStatement.setInt(3, item.getCount());
                                orderDetailStatement.addBatch();
                            }
                            orderDetailStatement.executeBatch();
                        }
                        return orderId;
                    } else {
                        throw new ApplicationException(String.format(CAN_T_READ_GENERATED_KEYS_FOR_TEMPLATE, shopOrder));
                    }
                }
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(FAILED_TO_ADD_ORDER_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public void updateStatus(ShopOrder shopOrder) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            statement.setString(1, shopOrder.getStatus().name());
            statement.setLong(2, shopOrder.getId());
            if (statement.executeUpdate() != 1) {
                throw new ApplicationException(
                        String.format(NOTHING_TO_UPDATE_BECAUSE_ORDER_NOT_FOUND_BY_ID_TEMPLATE, shopOrder.getId()));
            }
        } catch (SQLException exception) {
            throw new ApplicationException(
                    String.format(FAILED_TO_UPDATE_ORDER_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }
}
