import io.findify.s3mock.S3Mock;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class S3MockServer {
    protected static S3Mock api;


    @BeforeClass
    public static void S3MockServer(){
        api = new S3Mock.Builder().withPort(7071).withInMemoryBackend().build();
        api.start();
    }

    @AfterClass
    public static void stopServer(){
        api.stop();
    }
}
