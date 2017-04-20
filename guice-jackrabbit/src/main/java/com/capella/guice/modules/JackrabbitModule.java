package com.capella.guice.modules;

import com.capella.guice.services.JackRabbitOperations;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;

import javax.inject.Named;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.io.File;

/**
 * @author Ramesh Rajendran
 */
public class JackrabbitModule extends AbstractModule {


    @Provides
    public RepositoryConfig getRepositoryConfig(@Named("jackrabbit.repo.xml.path") String configurationPath,
                                                @Named("jackrabbit.datastore.location") String dataStorePath) throws ConfigurationException {
        return RepositoryConfig.create(new File(configurationPath), new File(dataStorePath));
    }


    @Provides
    public Repository getRepository(RepositoryConfig repositoryConfig) throws ConfigurationException {
        return new TransientRepository(repositoryConfig);
    }

    @Provides
    public SimpleCredentials getSimpleCredentials(@Named("jackrabbit.username") String username,
                                                  @Named("jackrabbit.password") String password) throws ConfigurationException {
        return new SimpleCredentials(username, password.toCharArray());
    }

    @Provides
    public Session getSession(Repository repository, SimpleCredentials simpleCredentials) throws RepositoryException {
        return repository.login(simpleCredentials);
    }

    @Override
    protected void configure() {
        bind(JackRabbitOperations.class);
    }
}
