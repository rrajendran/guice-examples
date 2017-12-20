package unit.com.capella.arangodb.service;

import com.capella.arangodb.service.ArangodbService;
import com.capella.arangodb.service.entity.S3Document;
import com.capella.arangodb.service.guice.modules.ArangodbModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Copyright 2017 (c) Mastek UK Ltd
 * <p>
 * Created on : 20/12/2017
 *
 * @author Ramesh Rajendran
 */
public class ArangodbServiceImplTest {
    Injector injector = Guice.createInjector(new ArangodbModule());
    ArangodbService arangodbService = injector.getInstance(ArangodbService.class);

    @Test
    public void save() throws Exception {
        S3Document s3document = new S3Document();
        s3document.setDocumentId(UUID.randomUUID().toString());
        s3document.setDocumentName("test.txt");
        s3document.addProperty("name", "test");
        s3document.addProperty("format", "text");
        String id = arangodbService.save(s3document);
        System.out.println("Document Id :" + id);
        S3Document s3Document = arangodbService.get(id, S3Document.class);
        System.out.println("body : " + s3Document.toString());
        assertThat(s3Document, CoreMatchers.is(s3Document));

    }

}