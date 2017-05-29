import com.capella.guice.domain.OakDocument;
import com.capella.guice.modules.ApplicationModule;
import com.capella.guice.services.OakOperations;
import com.capella.guice.utils.StreamUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.jcr.RepositoryException;
import java.io.*;
import java.net.URL;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Ramesh Rajendran
 */

public class OakFileRepositoryTest {
    private static OakOperations oakOperations;

    @BeforeClass
    public static void init() {
        Injector injector = Guice.createInjector(new ApplicationModule());
        oakOperations = injector.getInstance(OakOperations.class);
    }

    @Test
    public void testPdfStore() throws IOException, RepositoryException {
        URL url = OakFileRepositoryTest.class.getClassLoader().getResource("sample.pdf");
        File file = FileUtils.toFile(url);
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        String path = "hello/pdfs";

        String identifier = oakOperations.saveDocument(stream, "sample.pdf", "application/pdf");
        System.out.println(identifier);
        assertThat(identifier, is(notNullValue()));

        //InputStream inputStream = jackRabbitOperations.readBinaryFile(path);

        OakDocument fileByIdentifier = oakOperations.getDocumentById(identifier);


        StreamUtils.contentEquals(fileByIdentifier.getOakContentStream().getInputStream(), OakFileRepositoryTest.class.getClassLoader().getResourceAsStream("sample.pdf"));


        Map<String, String> property = oakOperations.getProperty(identifier);

        property.keySet().stream().forEach(System.out::println);
    }
}