package com.lee.shop.dao.impl;

import com.lee.shop.dao.UserDao;
import com.lee.shop.exception.ApplicationException;
import com.lee.shop.jdbc.JdbcConnectionPool;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private final static String NEW_USER = "INSERT INTO user(firstname, lastname, email, password, phone) VALUES (?, ?, ?, ?, ?)";
    private final static String ALL_USERS = "SELECT * FROM user ORDER BY id DESC";
    private final static String USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private final static String USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private final static String UPDATE_USER = "UPDATE user SET firstname = ?, lastname = ?, password = ?, phone = ?, role = ?, active = ? WHERE id = ?";

    private final static String USER_ID = "id";
    private final static String FIRST_NAME = "firstname";
    private final static String LAST_NAME = "lastname";
    private final static String EMAIL = "email";
    private final static String PHONE = "phone";
    private final static String PASSWORD = "password";
    private final static String ROLE = "role";
    private final static String ACTIVE = "active";

    private static final String CAN_T_GET_ALL_USERS_TEMPLATE = "Can't get all users: %s";
    private static final String CAN_T_GET_USER_BY_EMAIL_TEMPLATE = "Can't get user by email = %s: %s";
    private static final String CAN_T_GET_USER_BY_ID_TEMPLATE = "Can't get user by id = %s: %s";
    private static final String CAN_T_READ_GENERATED_KEYS_FOR_TEMPLATE = "Can't read generated keys for: %s";
    private static final String FAILED_TO_ADD_USER_TEMPLATE = "Failed to add user: %s";
    private static final String NOTHING_TO_UPDATE_BECAUSE_USER_NOT_FOUND_BY_EMAIL_TEMPLATE = "Nothing to update, because user not found by email: %s";
    private static final String FAILED_TO_UPDATE_USER_TEMPLATE = "Failed to update user: %s";

    public UserDaoImpl(JdbcConnectionPool jdbcConnectionPool) {
        super(jdbcConnectionPool);
    }

    @Override
    public List<User> getAll() {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ALL_USERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(convertResultSetToUser(resultSet));
                }
                return users;
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(CAN_T_GET_ALL_USERS_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public User getByEmail(String email) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return convertResultSetToUser(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(CAN_T_GET_USER_BY_EMAIL_TEMPLATE, email, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public User getById(Long id) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(USER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return convertResultSetToUser(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(CAN_T_GET_USER_BY_ID_TEMPLATE, id, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    private User convertResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setFirstname(resultSet.getString(FIRST_NAME));
        user.setLastname(resultSet.getString(LAST_NAME));
        user.setEmail(resultSet.getString(EMAIL));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setPhone(resultSet.getString(PHONE));
        user.setRole(Role.valueOf(resultSet.getString(ROLE)));
        user.setActive(resultSet.getBoolean(ACTIVE));
        return user;
    }

    @Override
    public Long create(User user) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(NEW_USER, new String[]{USER_ID})) {
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhone());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new ApplicationException(String.format(CAN_T_READ_GENERATED_KEYS_FOR_TEMPLATE, user));
                }
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(FAILED_TO_ADD_USER_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public void update(User user) {
        Connection connection = getJdbcConnectionPool().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole().name());
            statement.setBoolean(6, user.isActive());
            statement.setLong(7, user.getId());
            if (statement.executeUpdate() != 1) {
                throw new ApplicationException(String.format(NOTHING_TO_UPDATE_BECAUSE_USER_NOT_FOUND_BY_EMAIL_TEMPLATE, user.getEmail()));
            }
        } catch (SQLException exception) {
            throw new ApplicationException(String.format(FAILED_TO_UPDATE_USER_TEMPLATE, exception.getMessage()), exception);
        } finally {
            getJdbcConnectionPool().releaseConnection(connection);
        }
    }
}
