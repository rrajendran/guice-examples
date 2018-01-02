package unit.com.capella.mongodb.service;

import com.arangodb.ArangoCursor;
import com.arangodb.util.MapBuilder;
import com.capella.mongodb.service.ArangodbService;
import com.capella.mongodb.service.entity.S3Document;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Copyright 2017 (c) Mastek UK Ltd
 * <p>
 * Created on : 20/12/2017
 *
 * @author Ramesh Rajendran
 */
public class ArangodbServiceImplTest {
    Injector injector = Guice.createInjector(new com.capella.mongodb.service.guice.modules.MongodbModule());
    ArangodbService arangodbService = injector.getInstance(ArangodbService.class);
    private static String DOCUMENT_KEY = null;

    @Before
    public void saveDocument() throws Exception {
        S3Document s3document = new S3Document();
        String documentId = "001";
        s3document.setDocumentId(documentId);
        s3document.setDocumentName("test.txt");
        s3document.addProperty("name", "test");
        s3document.addProperty("format", "text");
        DOCUMENT_KEY = arangodbService.save(s3document);

        assertThat(DOCUMENT_KEY, is(documentId));
    }

    @Test
    public void getDocument() throws Exception {
        S3Document s3Document = arangodbService.get(DOCUMENT_KEY, S3Document.class);
        System.out.println("body : " + s3Document.toString());
        assertThat(s3Document.getDocumentId(), is(DOCUMENT_KEY));
        assertThat(s3Document.getDocumentName(), is("test.txt"));
        assertThat(s3Document.getProperties().get("name"), is("test"));
        assertThat(s3Document.getProperties().get("format"), is("text"));
    }


    @Test
    public void updateDocument() throws Exception {
        S3Document s3document = new S3Document();
        s3document.setDocumentId(DOCUMENT_KEY);
        s3document.setDocumentName("UpdateDocument.txt");
        s3document.addProperty("name", "UpdateDocument");
        s3document.addProperty("format", "text");

        String updateDocumentKey = arangodbService.update(DOCUMENT_KEY, s3document);
        assertThat(updateDocumentKey, is(DOCUMENT_KEY));

        S3Document s3DocumentUpdated = arangodbService.get(DOCUMENT_KEY, S3Document.class);
        assertThat(s3DocumentUpdated.getDocumentName(), is("UpdateDocument.txt"));
        assertThat(s3DocumentUpdated.getProperties().get("name"), is("UpdateDocument"));
        assertThat(s3DocumentUpdated.getProperties().get("format"), is("text"));
    }


    @Test
    public void getDocumentAsJson() throws Exception {
        String s3DocumentUpdated = arangodbService.get(DOCUMENT_KEY, String.class);
        assertThat(s3DocumentUpdated, is(notNullValue()));
    }


    @Test
    public void testQuery() {
        String query = "FOR t IN documents FILTER t.key == @keyName RETURN t";
        Map<String, Object> bindVars = new MapBuilder().put("keyName", DOCUMENT_KEY).get();

        ArangoCursor<S3Document> response = arangodbService.query(query, bindVars, null, S3Document.class);
        while (response.hasNext()) {
            S3Document s3Document = response.next();
            assertThat(s3Document.getDocumentName(), is(DOCUMENT_KEY));
        }
    }

    @After
    public void tearDown() {
        arangodbService.delete(DOCUMENT_KEY);
    }
}