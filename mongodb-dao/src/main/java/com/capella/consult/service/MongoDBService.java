package com.capella.consult.service;

import com.capella.consult.service.entity.S3Document;

/**
 * @author Ramesh Rajendran
 */
public interface MongoDBService {

    void save(S3Document object);

    <T> T get(String key, Class<T> T);

    void delete(String key);

    String updateDocument(String documentId, Object object);

    <T> T query(String query, Class<T> type);

}
