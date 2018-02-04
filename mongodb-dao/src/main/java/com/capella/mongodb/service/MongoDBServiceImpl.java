package com.capella.mongodb.service;

import com.capella.mongodb.service.entity.S3Document;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.inject.Inject;

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

    @Inject
    private ObjectMapper objectMapper;
    public void save(S3Document document) {
        try {
            mongoCollection.insertOne(Document.parse(objectMapper.writeValueAsString(document)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T get(String documentId, Class<T> className) {
        BasicDBObject object = new BasicDBObject();
        object.put("_id", documentId);
        FindIterable<Document> documents = mongoCollection.find(object);
        MongoCursor<Document> iterator = documents.iterator();
        return iterator.hasNext() ? (T) iterator.next() : null;
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
