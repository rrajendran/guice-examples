package com.capella.mongodb.service;

import com.arangodb.ArangoDatabase;

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

    }

    @Override
    public <T> T get(String documentId, Class<T> className) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public String updateDocument(String key, Object object) {

    }

    @Override
    public <T> T query(String query, Class<T> type) {

    }
}
