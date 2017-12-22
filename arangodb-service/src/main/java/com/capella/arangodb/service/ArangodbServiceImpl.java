package com.capella.arangodb.service;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.DocumentUpdateEntity;
import com.arangodb.model.AqlQueryOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

/**
 * Copyright 2017 (c) Mastek UK Ltd
 * <p>
 * Created on : 20/12/2017
 *
 * @author Ramesh Rajendran
 */
public class ArangodbServiceImpl implements ArangodbService {

    @Inject
    private ArangoDatabase arangoDatabase;

    @Inject
    @Named("arangodb.collection.name")
    private String collectionName;

    public String save(Object object) {
        DocumentCreateEntity<Object> s3DocumentDocumentCreateEntity =
                arangoDatabase.collection(collectionName).insertDocument(object);
        return s3DocumentDocumentCreateEntity.getKey();
    }

    @Override
    public <T> T get(String documentId, Class<T> className) {
        return arangoDatabase.collection(collectionName).getDocument(documentId, className);
    }

    @Override
    public void delete(String key) {
        arangoDatabase.collection(collectionName).deleteDocument(key);
    }

    @Override
    public String update(String key, Object object) {
        DocumentUpdateEntity<Object> objectDocumentUpdateEntity = arangoDatabase.collection(collectionName).updateDocument(key, object);
        return objectDocumentUpdateEntity.getKey();
    }

    @Override
    public <T> ArangoCursor<T> query(String query, Map<String, Object> bindVars, AqlQueryOptions options, Class<T> type) {
        return arangoDatabase.query(query, bindVars, options, type);
    }
}
