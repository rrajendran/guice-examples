package com.capella.guice.utils;

import com.capella.guice.exceptions.Exceptions;

import java.io.InputStream;
import java.util.Properties;


/**
 * @author Ramesh Rajendran
 */
public class PropertiesProvider {

    private static final Properties properties = new Properties();


    static {
        InputStream inputStream = PropertiesProvider.class
                .getClassLoader()
                .getResourceAsStream("guice-jackrabbit-oak.properties");

        Exceptions.uncheck(() -> {
            properties.load(inputStream);
            return null;
        });
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Properties getProperties() {
        return properties;
    }
}
