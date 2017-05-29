package com.capella.guice.utils;

public interface PropertiesKeys {

    String JDBC_DRIVER = "persistence.jdbc.driver";
    String JDBC_URL = "persistence.jdbc.url";
    String JDBC_USERNAME = "persistence.jdbc.username";
    String JDBC_PASSWORD = "persistence.jdbc.password";
    String ENVIRONMENT = "persistence.environment";
    String SCHEMA = "persistence.schema";
    String JDBC_DIALECT = "persistence.jdbc.dialect";
    String MAX_RESULTSET = "persistence.resultset.max";
    String CONNECTION_POOLING_MIN_SIZE = "persistence.connectionpooling.min.size";
    String CONNECTION_POOLING_MAX_SIZE = "persistence.connectionpooling.max.size";
    String CONNECTION_POOLING_TIMEOUT = "persistence.connectionpooling.timeout";
    String CONNECTION_POOLING_MAX_STATEMENTS = "persistence.connectionpooling.max_statements";
    String CONNECTION_POOLING_IDLE_TEST_PERIOD = "persistence.connectionpooling.idle_test_period";

}

