package unit.com.capella.aws.s3.bucket.services;

import com.capella.aws.s3.bucket.guice.modules.AmazonS3Module;
import com.capella.aws.s3.bucket.services.AmazonS3Service;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import java.io.ByteArrayInputStream;

/**
 * @author Ramesh Rajendran
 */
public class AmazonS3ServiceTest {
    private AmazonS3Service amazonS3Service;

    public AmazonS3ServiceTest() {
        Injector injector = Guice.createInjector(new AmazonS3Module());
        amazonS3Service = injector.getInstance(AmazonS3Service.class);
    }

    @Test
    public void putObject() throws Exception {
        amazonS3Service.putObject("testbucket", "hello", new ByteArrayInputStream("world".getBytes()), null);
    }

    @Test
    public void getObject() throws Exception {
    }

    @Test
    public void deleteObject() throws Exception {
    }

    @Test
    public void copyObject() throws Exception {
    }

}