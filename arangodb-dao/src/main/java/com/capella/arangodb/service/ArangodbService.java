package com.capella.arangodb.service;

import com.arangodb.ArangoCursor;
import com.arangodb.model.AqlQueryOptions;

import java.util.Map;

/**
 * @author Ramesh Rajendran
 */
public interface ArangodbService {

    String save(Object object);

    <T> T get(String key, Class<T> T);

    void delete(String key);

    String updateDocument(String documentId, Object object);

    <T> ArangoCursor<T> query(String query, Map<String, Object> bindVars, AqlQueryOptions options, Class<T> type);
}
