package com.capella.guice.utils;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author DeepakB
 */
public final class Environment {
    public static final Logger LOGGER = getLogger(Environment.class);

    private Environment() {
    }

    public static boolean isLocal() {
        final String env = PropertiesProvider.getProperty(PropertiesKeys.ENVIRONMENT);
        LOGGER.info("env = " + env);
        return "local".equals(env);
    }


}
