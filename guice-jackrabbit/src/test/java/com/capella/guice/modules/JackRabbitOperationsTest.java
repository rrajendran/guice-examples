package com.capella.guice.modules;

import com.capella.guice.services.JackRabbitOperations;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.io.FileUtils;
import org.apache.tika.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.jcr.ItemNotFoundException;
import javax.jcr.RepositoryException;
import java.io.*;
import java.net.URL;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by ramesh on 14/01/2016.
 */
public class JackRabbitOperationsTest {

    private static JackRabbitOperations jackRabbitOperations;


    @BeforeClass
    public static void init() {
        Injector injector = Guice.createInjector(new ApplicationModule());
        jackRabbitOperations = injector.getInstance(JackRabbitOperations.class);

    }

    @Test
    public void testAddText() throws Exception {
        jackRabbitOperations.addText("hello", "Test message");
        String text = jackRabbitOperations.retrieveText("hello");
        assertThat(text, is("Test message"));
    }

    @Test
    public void testPdfStore() throws IOException, RepositoryException {
        URL url = JackRabbitOperations.class.getClassLoader().getResource("sample.pdf");
        File file = FileUtils.toFile(url);
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        String path = "hello/pdfs";

        String identifier = jackRabbitOperations.writeBinaryFile(path, stream);
        System.out.println(identifier);
        assertThat(identifier, is(notNullValue()));

        //InputStream inputStream = jackRabbitOperations.readBinaryFile(path);

        InputStream fileByIdentifier = jackRabbitOperations.readBinaryFile(identifier);


        IOUtils.contentEquals(fileByIdentifier, JackRabbitOperations.class.getClassLoader().getResourceAsStream("sample.pdf"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenIdentifierIsNotProvided() throws Exception {
        jackRabbitOperations.readBinaryFile(null);
    }


    @Test(expected = ItemNotFoundException.class)
    public void shouldThrowExceptionWhenIdentifierIsNotFound() throws Exception {
        jackRabbitOperations.readBinaryFile(UUID.randomUUID().toString());
    }

    @Test
    public void testRemoveNodes() throws RepositoryException {
        jackRabbitOperations.removeNode("hello/pdfs");

        assertThat(jackRabbitOperations.hasNode("hello/pdfs"), is(false));
    }

}