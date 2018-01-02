package com.capella.mongodb.service;

/**
 * @author Ramesh Rajendran
 */
public interface ArangodbService {

    String save(Object object);

    <T> T get(String key, Class<T> T);

    void delete(String key);

    String updateDocument(String documentId, Object object);

    <T> T query(String query, Class<T> type);
}
