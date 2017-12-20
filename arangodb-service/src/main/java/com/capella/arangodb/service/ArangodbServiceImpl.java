package com.capella.arangodb.service;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;

import javax.inject.Inject;
import javax.inject.Named;

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
}
