package com.capella.vault.guice.modules;

import java.io.InputStream;
import java.util.Properties;

import com.capella.vault.exceptions.Exceptions;
import com.google.inject.Provider;

public class PropertiesProvider implements Provider<Properties> {

    private static final Properties properties = new Properties();

    static {
        InputStream inputStream = PropertiesProvider.class
                        .getClassLoader()
                        .getResourceAsStream("vault-service.properties");

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

    @Override
    public Properties get() {
        return properties;
    }
}
