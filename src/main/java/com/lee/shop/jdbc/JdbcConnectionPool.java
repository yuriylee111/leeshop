package com.lee.shop.jdbc;

import com.lee.shop.exception.ApplicationException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class JdbcConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(JdbcConnectionPool.class);

    private static final int GET_NEW_CONNECTION_TIMEOUT_IN_MILLIS = 1_000;

    private static final String URL = "db.url";
    private static final String USER = "db.user";
    private static final String PASSWORD = "db.password";
    private static final String DRIVER = "db.driver";
    private static final String POOL_SIZE = "db.pool.size";

    private static final String POOL_RELEASED_TEMPLATE = "Pool released";
    private static final String CAN_T_RETRIEVE_CONNECTION_FROM_POOL_ALL_CONNECTIONS_ARE_BUSY_TEMPLATE =
            "Can't retrieve connection from pool: all connections are busy";
    private static final String CAN_T_CREATE_JDBC_CONNECTION_POOL_TEMPLATE = "Can't create JDBC connection pool: %s";
    private static final String POOL_CREATED_SIZE_TEMPLATE = "Pool created: size = %s";
    private static final String JDBC_DRIVER_CLASS_NOT_FOUND_TEMPLATE = "Jdbc driver class not found: %s";
    private static final String CAN_T_RETRIEVE_CONNECTION_FROM_POOL_TEMPLATE = "Can't retrieve connection from pool: %s";
    private static final String ERROR_DURING_CLOSING_JDBC_CONNECTION_TEMPLATE = "Error during closing JDBC connection: %s";

    private final BlockingQueue<Connection> pool;

    public JdbcConnectionPool(Properties applicationProperties) {
        registerJdbDriver(applicationProperties.getProperty(DRIVER));
        int poolSize = Integer.parseInt(applicationProperties.getProperty(POOL_SIZE));
        pool = new ArrayBlockingQueue<>(poolSize);
        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = getNewConnection(applicationProperties);
                pool.add(connection);
            }
        } catch (SQLException sqlException) {
            throw new ApplicationException(
                    String.format(CAN_T_CREATE_JDBC_CONNECTION_POOL_TEMPLATE, sqlException.getMessage()), sqlException);
        }
        LOGGER.info(String.format(POOL_CREATED_SIZE_TEMPLATE, poolSize));
    }

    private void registerJdbDriver(String jdbcDriverClass) {
        try {
            Class.forName(jdbcDriverClass);
        } catch (ClassNotFoundException exception) {
            throw new ApplicationException(String.format(JDBC_DRIVER_CLASS_NOT_FOUND_TEMPLATE, jdbcDriverClass));
        }
    }

    private Connection getNewConnection(Properties applicationProperties) throws SQLException {
        String url = applicationProperties.getProperty(URL);
        String user = applicationProperties.getProperty(USER);
        String password = applicationProperties.getProperty(PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        try {
            Connection connection = pool.poll(GET_NEW_CONNECTION_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS);
            if (connection != null) {
                return connection;
            } else {
                throw new ApplicationException(CAN_T_RETRIEVE_CONNECTION_FROM_POOL_ALL_CONNECTIONS_ARE_BUSY_TEMPLATE);
            }
        } catch (InterruptedException interruptedException) {
            throw new ApplicationException(
                    String.format(CAN_T_RETRIEVE_CONNECTION_FROM_POOL_TEMPLATE, interruptedException.getMessage()), interruptedException);
        }
    }

    public void releaseConnection(Connection connection) {
        pool.add(connection);
    }

    public void releasePool() {
        for (Connection connection : pool) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                LOGGER.error(String.format(ERROR_DURING_CLOSING_JDBC_CONNECTION_TEMPLATE, sqlException.getMessage()), sqlException);
            }
        }
        pool.clear();
        LOGGER.info(POOL_RELEASED_TEMPLATE);
    }
}