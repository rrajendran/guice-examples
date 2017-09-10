package com.capella.guice.modules;

import com.capella.guice.services.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import org.apache.jackrabbit.oak.blob.cloud.aws.s3.SharedS3DataStore;
import org.apache.jackrabbit.oak.blob.cloud.s3.S3DataStore;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.blob.datastore.DataStoreBlobStore;
import org.apache.jackrabbit.oak.plugins.document.DocumentMK;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeState;
import org.apache.jackrabbit.oak.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;
import org.apache.jackrabbit.oak.spi.blob.BlobStore;

import javax.inject.Named;
import javax.jcr.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Ramesh Rajendran
 */
public class JackrabbitOakModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(OakFileRepository.class);
        binder.bind(OakOperations.class).to(OakOperationsImpl.class);

    }

    @Provides
    public Session sessionFactory(DocumentNodeStore documentNodeStore, Credentials credentials) throws RepositoryException, IOException,
            InvalidFileStoreVersionException {

        Repository repo = new Jcr(documentNodeStore).createRepository();
        Session session = repo.login(credentials);

        NodeRegistration r = new NodeRegistrationImpl(session);
        r.register();

        return session;
    }

    @Provides
    public DocumentNodeStore getDocumentNodeStore(S3DataStore s3DataStore) {
        BlobStore blobstore = new DataStoreBlobStore(s3DataStore);
        DocumentMK.Builder builder = new DocumentMK.Builder().setBlobStore(blobstore);
        return new DocumentNodeStore(builder);
    }

    @Provides
    public S3DataStore getSharedS3DataStore() throws RepositoryException {
        S3DataStore s3DataStore = new S3DataStore();
        s3DataStore.setProperties(PropertiesProvider.getProperties());
        s3DataStore.setCacheSize(100000);
        //s3DataStore.setTouchAsync(true);
        s3DataStore.init("target/");
        return s3DataStore;
    }

    @Provides
    public Credentials getCredentials(@Named("oak.username") String oakuser,
                                      @Named("oak.password") String oakPassword) {
        return new SimpleCredentials(oakuser, oakPassword.toCharArray());
    }

    @Provides
    public SegmentNodeStore getSegmentNodeStore(@Named("oak.repository.path") String repoPath) throws IOException, InvalidFileStoreVersionException {
        FileStore fs = FileStoreBuilder.fileStoreBuilder(new File(repoPath)).build();
        return SegmentNodeStoreBuilders.builder(fs).build();
    }


}
