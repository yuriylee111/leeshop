package com.lee.shop.dao.impl;

import com.lee.shop.jdbc.JdbcConnectionPool;

public class BaseDaoImpl {

    private final JdbcConnectionPool jdbcConnectionPool;

    public BaseDaoImpl(JdbcConnectionPool jdbcConnectionPool) {
        this.jdbcConnectionPool = jdbcConnectionPool;
    }

    public JdbcConnectionPool getJdbcConnectionPool() {
        return jdbcConnectionPool;
    }
}
