package com.lee.shop.jdbc;

import com.lee.shop.exception.ApplicationException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class JdbcConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(JdbcConnectionPool.class);

    private static final int GET_NEW_CONNECTION_TIMEOUT_IN_MILLIS = 100;
    private static final int GET_NEW_CONNECTION_TRY_COUNT = 10;

    private static final String URL = "db.url";
    private static final String USER = "db.user";
    private static final String PASSWORD = "db.password";
    private static final String DRIVER = "db.driver";
    private static final String POOL_SIZE = "db.pool.size";

    private final LinkedList<Connection> pool = new LinkedList<>();

    public JdbcConnectionPool(Properties applicationProperties) {
        registerJdbDriver(applicationProperties.getProperty(DRIVER));
        int poolSize = Integer.parseInt(applicationProperties.getProperty(POOL_SIZE));
        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = getNewConnection(applicationProperties);
                pool.addLast(connection);
            }
        } catch (SQLException sqlException) {
            throw new ApplicationException("Can't create JDBC connection pool: " + sqlException.getMessage(), sqlException);
        }
        LOGGER.info("Pool created: size = " + poolSize);
    }

    private void registerJdbDriver(String jdbcDriverClass) {
        try {
            Class.forName(jdbcDriverClass);
        } catch (ClassNotFoundException exception) {
            throw new ApplicationException("Jdbc driver class not found: " + jdbcDriverClass);
        }
    }

    private Connection getNewConnection(Properties applicationProperties) throws SQLException {
        String url = applicationProperties.getProperty(URL);
        String user = applicationProperties.getProperty(USER);
        String password = applicationProperties.getProperty(PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        Connection connection;
        for (int i = 0; i < GET_NEW_CONNECTION_TRY_COUNT; i++) {
            synchronized (pool) {
                connection = pool.pollFirst();
            }
            if (connection != null) {
                return connection;
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(GET_NEW_CONNECTION_TIMEOUT_IN_MILLIS);
                } catch (InterruptedException exception) {
                    LOGGER.warn("Can't sleep current thread: " + Thread.currentThread(), exception);
                }
            }
        }
        throw new ApplicationException("Can't retrieve connection from pool: all connections are busy");
    }

    public void releaseConnection(Connection connection) {
        synchronized (pool) {
            pool.addLast(connection);
        }
    }

    public void releasePool() {
        synchronized (pool) {
            for (Connection connection : pool) {
                try {
                    connection.close();
                } catch (SQLException sqlException) {
                    LOGGER.error("Error during closing JDBC connection: " + sqlException.getMessage(), sqlException);
                }
            }
            pool.clear();
        }
        LOGGER.info("Pool released");
    }
}