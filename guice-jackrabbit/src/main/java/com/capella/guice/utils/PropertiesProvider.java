package com.capella.guice.utils;

import java.io.InputStream;
import java.util.Properties;

import static com.capella.guice.exceptions.Exceptions.uncheck;


/**
 * @author Ramesh Rajendran
 */
public class PropertiesProvider {

    private static final Properties properties = new Properties();


    static {
        InputStream inputStream = PropertiesProvider.class
                .getClassLoader()
                .getResourceAsStream("guice-jackrabbit.properties");

        uncheck(() -> {
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
