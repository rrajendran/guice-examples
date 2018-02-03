package com.capella.mongodb.service;

import com.capella.mongodb.service.entity.S3Document;
import org.bson.Document;

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
