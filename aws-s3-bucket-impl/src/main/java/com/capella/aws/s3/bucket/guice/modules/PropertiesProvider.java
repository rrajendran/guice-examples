package com.capella.aws.s3.bucket.guice.modules;

import com.google.inject.Provider;

import java.io.InputStream;
import java.util.Properties;

import static com.capella.aws.s3.bucket.exceptions.Exceptions.uncheck;

public class PropertiesProvider implements Provider<Properties> {

    private static final Properties properties = new Properties();

    static {
        InputStream inputStream = PropertiesProvider.class
                .getClassLoader()
                .getResourceAsStream("aws-s3-bucket.properties");

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

    @Override
    public Properties get() {
        return properties;
    }
}
