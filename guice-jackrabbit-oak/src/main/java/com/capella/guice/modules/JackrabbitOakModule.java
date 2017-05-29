package com.capella.guice.modules;

import com.capella.guice.services.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;

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
    public Session sessionFactory(@Named("oak.repository.path") String repoPath, Credentials credentials) throws RepositoryException, IOException,
            InvalidFileStoreVersionException {
        FileStore fs = FileStoreBuilder.fileStoreBuilder(new File(repoPath)).build();
        SegmentNodeStore ns = SegmentNodeStoreBuilders.builder(fs).build();
        Repository repo = new Jcr(ns).createRepository();

        Session session = repo.login(credentials);

        NodeRegistration r = new NodeRegistrationImpl(session);
        r.register();

        return session;
    }

    @Provides
    public Credentials getCredentials(@Named("oak.username") String oakuser,
                                      @Named("oak.password") String oakPassword) {
        return new SimpleCredentials(oakuser, oakPassword.toCharArray());
    }
}
