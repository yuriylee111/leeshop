package com.lee.shop.listener;

import com.lee.shop.ComponentFactory;
import com.lee.shop.Constants;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ApplicationListener.class);

    private ComponentFactory componentFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        componentFactory = ComponentFactory.getInstance();

        sce.getServletContext().setAttribute(Constants.CATEGORY_LIST, componentFactory.getCategoryDao().getAll());

        LOGGER.info("Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (componentFactory != null) {
            componentFactory.getJdbcConnectionPool().releasePool();
        }
        LOGGER.info("Application destroyed");
    }
}
