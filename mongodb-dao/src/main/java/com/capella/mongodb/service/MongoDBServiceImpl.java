package com.capella.mongodb.service;

import com.capella.mongodb.service.entity.S3Document;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import org.bson.Document;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Copyright 2017 (c) Mastek UK Ltd
 * <p>
 * Created on : 20/12/2017
 *
 * @author Ramesh Rajendran
 */
public class MongoDBServiceImpl implements MongoDBService {

    @Inject
    private MongoCollection<Document> mongoCollection;

    public void save(S3Document document) {
        Document doc = new Document();
        doc.put(document.getDocumentId(), document.getProperties());
        mongoCollection.insertOne(doc);
    }

    @Override
    public <T> T get(String documentId, Class<T> className) {
        return null;
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public String updateDocument(String key, Object object) {
        return null;
    }

    @Override
    public <T> T query(String query, Class<T> type) {
        return null;
    }
}
