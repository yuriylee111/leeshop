package com.lee.shop;

import com.lee.shop.exception.ApplicationException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationPropertiesLoader {

    private static final Logger LOGGER = Logger.getLogger(ApplicationPropertiesLoader.class);

    private static final String CLASSPATH_APPLICATION_PROPERTIES = "application.properties";
    private static final String FILE_LEESHOP_PROPERTIES = "/leeshop.properties";

    private static final String CLASSPATH_PREFIX = "classpath:/";
    private static final String CONFIG_FILE_S_NOT_FOUND_TEMPLATE = "Config file: '%s' not found";
    private static final String CAN_T_LOAD_PROPERTIES_FROM_TEMPLATE = "Can't load properties from %s";

    public Properties load() {
        Properties properties = new Properties();
        properties.putAll(loadApplicationPropertiesFromClasspathResource());
        properties.putAll(loadApplicationPropertiesFromConfigFile());
        return properties;
    }

    private Properties loadApplicationPropertiesFromClasspathResource() {
        return loadApplicationPropertiesFromInputStream(
                ComponentFactory.class.getClassLoader().getResourceAsStream(CLASSPATH_APPLICATION_PROPERTIES),
                CLASSPATH_PREFIX + CLASSPATH_APPLICATION_PROPERTIES);
    }

    private Properties loadApplicationPropertiesFromConfigFile() {
        try(InputStream inputStream = new FileInputStream(FILE_LEESHOP_PROPERTIES)) {
            return loadApplicationPropertiesFromInputStream(inputStream,  FILE_LEESHOP_PROPERTIES);
        } catch (FileNotFoundException ignore) {
            LOGGER.warn(String.format(CONFIG_FILE_S_NOT_FOUND_TEMPLATE, FILE_LEESHOP_PROPERTIES));
            return new Properties();
        } catch (IOException exception) {
            throw new ApplicationException(String.format(CAN_T_LOAD_PROPERTIES_FROM_TEMPLATE, FILE_LEESHOP_PROPERTIES), exception);
        }
    }

    private Properties loadApplicationPropertiesFromInputStream(InputStream inputStream, String description) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new ApplicationException(String.format(CAN_T_LOAD_PROPERTIES_FROM_TEMPLATE, description), exception);
        }
        return properties;
    }
}
