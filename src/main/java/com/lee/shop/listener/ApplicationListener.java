package com.lee.shop.listener;

import com.lee.shop.ComponentFactory;
import com.lee.shop.Constants;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ApplicationListener.class);

    private static final String APPLICATION_STARTED_TEMPLATE = "Application started";
    private static final String APPLICATION_DESTROYED_TEMPLATE = "Application destroyed";

    private ComponentFactory componentFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        componentFactory = ComponentFactory.getInstance();

        sce.getServletContext().setAttribute(Constants.CATEGORY_LIST, componentFactory.getCategoryDao().getAll());

        LOGGER.info(APPLICATION_STARTED_TEMPLATE);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (componentFactory != null) {
            componentFactory.getJdbcConnectionPool().releasePool();
        }
        LOGGER.info(APPLICATION_DESTROYED_TEMPLATE);
    }
}
