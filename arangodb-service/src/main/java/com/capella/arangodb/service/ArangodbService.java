package com.capella.arangodb.service;

/**
 * @author Ramesh Rajendran
 */
public interface ArangodbService {

    String save(Object object);

    <T> T get(String key, Class<T> T);
}
