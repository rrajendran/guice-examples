package com.capella.arangodb.service;

import com.arangodb.ArangoCursor;
import com.arangodb.model.AqlQueryOptions;

import java.util.Map;

/**
 * @author Ramesh Rajendran
 */
public interface ArangodbService {

    /**
     * Save document
     *
     * @param object A object
     * @return
     */
    String save(Object object);

    /**
     * Get document by key
     * @param key
     * @param T
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> T);

    /**
     * Delete document
     * @param key
     */
    void delete(String key);

    /**
     * Update a document
     *
     * @param documentId
     * @param object
     * @return
     */
    String update(String documentId, Object object);

    /**
     * Execute AQL Query
     * @param query
     * @param bindVars
     * @param options
     * @param type
     * @param <T>
     * @return
     */
    <T> ArangoCursor<T> query(String query, Map<String, Object> bindVars, AqlQueryOptions options, Class<T> type);
}
