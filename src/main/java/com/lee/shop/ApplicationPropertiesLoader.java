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

    public Properties load() {
        Properties properties = new Properties();
        properties.putAll(loadApplicationPropertiesFromClasspathResource());
        properties.putAll(loadApplicationPropertiesFromConfigFile());
        return properties;
    }

    private Properties loadApplicationPropertiesFromClasspathResource() {
        return loadApplicationPropertiesFromInputStream(
                ComponentFactory.class.getClassLoader().getResourceAsStream(CLASSPATH_APPLICATION_PROPERTIES),
                "classpath:/" + CLASSPATH_APPLICATION_PROPERTIES);
    }

    private Properties loadApplicationPropertiesFromConfigFile() {
        try(InputStream inputStream = new FileInputStream(FILE_LEESHOP_PROPERTIES)) {
            return loadApplicationPropertiesFromInputStream(inputStream,  FILE_LEESHOP_PROPERTIES);
        } catch (FileNotFoundException ignore) {
            LOGGER.warn("Config file: '" + FILE_LEESHOP_PROPERTIES + "' not found");
            return new Properties();
        } catch (IOException exception) {
            throw new ApplicationException("Can't load properties from " + FILE_LEESHOP_PROPERTIES, exception);
        }
    }

    private Properties loadApplicationPropertiesFromInputStream(InputStream inputStream, String description) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new ApplicationException("Can't load properties from " + description, exception);
        }
        return properties;
    }
}
