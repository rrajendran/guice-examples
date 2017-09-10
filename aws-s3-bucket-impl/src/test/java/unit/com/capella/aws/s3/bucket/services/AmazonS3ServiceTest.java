package unit.com.capella.aws.s3.bucket.services;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.capella.aws.s3.bucket.guice.modules.AmazonS3Module;
import com.capella.aws.s3.bucket.services.AmazonS3Service;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * @author Ramesh Rajendran
 */
public class AmazonS3ServiceTest extends S3MockServer{
    Injector injector = Guice.createInjector(new AmazonS3Module());
    AmazonS3Service  amazonS3Service = injector.getInstance(AmazonS3Service.class);
    private String BUCKET_NAME = "testbucket";
    private String KEY_NAME = "hello";

    @Before
    public void putObject() throws Exception {
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, KEY_NAME, new ByteArrayInputStream("world".getBytes()), null);
        amazonS3Service.putObject(request);
    }

    @Test
    public void testGetObject() throws IOException {
        S3Object object = amazonS3Service.getObject(BUCKET_NAME, KEY_NAME);
        assertEquals(displayTextInputStream(object.getObjectContent()),"world");
    }

    @Test
    public void testMoveObject() throws IOException {
        amazonS3Service.copyObject(BUCKET_NAME, KEY_NAME, "tempbucket", "tempkey");

        S3Object object = amazonS3Service.getObject("tempbucket", "tempkey");
        assertEquals(displayTextInputStream(object.getObjectContent()),"world");
    }

    @After
    public void tearDown(){
        amazonS3Service.deleteObject(BUCKET_NAME, KEY_NAME);

        try {
            S3Object object = amazonS3Service.getObject(BUCKET_NAME, KEY_NAME);
            fail("Should not find the object");
        } catch(AmazonS3Exception ex) {
            // do something with your exception
        }

    }
    private static String displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        return IOUtils.toString(reader);

    }

}