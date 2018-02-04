package unit.com.capella.mongodb.service;

import com.capella.mongodb.service.MongoDBService;
import com.capella.mongodb.service.entity.S3Document;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Copyright 2017 (c) Mastek UK Ltd
 * <p>
 * Created on : 20/12/2017
 *
 * @author Ramesh Rajendran
 */
public class MongoDBServiceImplTest {
    Injector injector = Guice.createInjector(new com.capella.mongodb.service.guice.modules.MongodbModule());
    MongoDBService mongoDBService = injector.getInstance(MongoDBService.class);
    private static String DOCUMENT_KEY = null;

    @Before
    public void saveDocument() throws Exception {
        S3Document s3document = new S3Document();
        s3document.setDocumentId(UUID.randomUUID().toString());
        s3document.setDocumentName("test.txt");
        s3document.addProperty("name", "test");
        s3document.addProperty("format", "text");
        DOCUMENT_KEY = s3document.getDocumentId();
        mongoDBService.save(s3document);
    }

    @Test
    public void getDocument() throws Exception {
        S3Document s3Document = mongoDBService.get(DOCUMENT_KEY, S3Document.class);
        System.out.println("body : " + s3Document.toString());
        assertThat(s3Document.getDocumentId(), is(DOCUMENT_KEY));
        assertThat(s3Document.getDocumentName(), is("test.txt"));
        assertThat(s3Document.getProperties().get("name"), is("test"));
        assertThat(s3Document.getProperties().get("format"), is("text"));
    }


    @After
    public void tearDown() {
        mongoDBService.delete(DOCUMENT_KEY);
    }
}