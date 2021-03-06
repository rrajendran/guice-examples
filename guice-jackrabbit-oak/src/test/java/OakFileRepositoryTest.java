import com.amazonaws.services.s3.AmazonS3Client;
import com.capella.guice.domain.OakDocument;
import com.capella.guice.modules.ApplicationModule;
import com.capella.guice.services.OakOperations;
import com.capella.guice.utils.StreamUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.io.FileUtils;
import org.apache.jackrabbit.oak.blob.cloud.s3.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Ramesh Rajendran
 */

public class OakFileRepositoryTest extends S3MockServer {
    private static OakOperations oakOperations;
    private String identifier;
    private AmazonS3Client s3Client;
    private Properties properties;
    public OakFileRepositoryTest() {
        Injector injector = Guice.createInjector(new ApplicationModule());
        oakOperations = injector.getInstance(OakOperations.class);
        properties = injector.getInstance(Properties.class);
        s3Client = Utils.openService(properties);

    }

    @Before
    public void testPdfStore() throws IOException, RepositoryException {
        URL url = OakFileRepositoryTest.class.getClassLoader().getResource("sample.pdf");
        File file = FileUtils.toFile(url);
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        String path = "hello/pdfs";

        identifier = oakOperations.saveDocument(stream, "sample.pdf", "application/pdf");
        System.out.println("********************" + identifier);
        assertThat(identifier, is(notNullValue()));
    }


    @Test
    public void shouldVerifyDocumentIsEqual() throws IOException {
        OakDocument fileByIdentifier = oakOperations.getDocumentById(identifier);
        StreamUtils.contentEquals(fileByIdentifier.getOakContentStream().getInputStream(), OakFileRepositoryTest.class.getClassLoader().getResourceAsStream("sample.pdf"));
    }


    @Test
    public void shouldUpdateMetadata() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("serviceDeliveryId", "10001");
        oakOperations.updateDocumentMetaData(map, identifier);

        Map<String, String> properties = oakOperations.getDocumentMetadataById(identifier);

        assertThat(properties.get("jcr:serviceDeliveryId"), is("10001"));
    }

    @After
    public void testDeleteDocumentById() {
        oakOperations.deleteDocumentById(identifier);
        OakDocument documentById = oakOperations.getDocumentById(identifier);
        assertThat(documentById, is(nullValue()));


    }
}