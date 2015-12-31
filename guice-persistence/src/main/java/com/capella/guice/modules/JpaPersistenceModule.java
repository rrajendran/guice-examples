package com.capella.guice.modules;

import com.capella.guice.utils.Environment;
import com.capella.guice.utils.PropertiesKeys;
import com.capella.guice.utils.PropertiesProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

import java.util.HashMap;

/**
 * @author Ramesh Rajendran
 */
public class JpaPersistenceModule extends AbstractModule {

    public void configure() {
        install(getPersistenceModule());

        bind(JpaInitializer.class).asEagerSingleton();
    }

    private Module getPersistenceModule() {
        if (Environment.isLocal()) {
            return new JpaPersistModule("test-persistence-unit");
        } else {
            return new JpaPersistModule("persistence-unit").properties(prepareProductionDbProperties());
        }
    }

    private HashMap<String, String> prepareProductionDbProperties() {
        return new HashMap<String, String>() {{
            put("hibernate.connection.driver_class", PropertiesProvider.getProperty(PropertiesKeys.JDBC_DRIVER));
            put("hibernate.connection.url", PropertiesProvider.getProperty(PropertiesKeys.JDBC_URL));
            put("hibernate.connection.username", PropertiesProvider.getProperty(PropertiesKeys.JDBC_USERNAME));
            put("hibernate.connection.password", PropertiesProvider.getProperty(PropertiesKeys.JDBC_PASSWORD));
            put("hibernate.default_schema", PropertiesProvider.getProperty(PropertiesKeys.SCHEMA));
            put("hibernate.c3p0.min_size", PropertiesProvider.getProperty(PropertiesKeys.CONNECTION_POOLING_MIN_SIZE));
            put("hibernate.c3p0.max_size", PropertiesProvider.getProperty(PropertiesKeys.CONNECTION_POOLING_MAX_SIZE));
            put("hibernate.c3p0.timeout", PropertiesProvider.getProperty(PropertiesKeys.CONNECTION_POOLING_TIMEOUT));
            put("hibernate.c3p0.max_statements", PropertiesProvider.getProperty(PropertiesKeys.CONNECTION_POOLING_MAX_STATEMENTS));
            put("hibernate.c3p0.idle_test_period", PropertiesProvider.getProperty(PropertiesKeys.CONNECTION_POOLING_IDLE_TEST_PERIOD));
            put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        }};
    }
}
