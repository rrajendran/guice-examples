package com.capella.arangodb.service.guice.modules;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.capella.arangodb.service.ArangodbService;
import com.capella.arangodb.service.ArangodbServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.inject.Named;
import java.util.Properties;

import static com.google.inject.name.Names.bindProperties;

/**
 * @author Ramesh Rajendran
 */
public class ArangodbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ArangodbService.class).to(ArangodbServiceImpl.class);
        binder().bind(Properties.class).toProvider(PropertiesProvider.class).in(Singleton.class);
        bindProperties(binder(), PropertiesProvider.getProperties());
    }


    @Provides
    public ArangoDB getArangodbInstance(@Named("arangodb.hosts") String hostName, @Named("arangodb.port") Integer port,
                                        @Named("arangodb.connections.max") Integer maxConnections, @Named("arangodb.password") String password,
                                        @Named("arangodb.user") String userName, @Named("arangodb.timeout") Integer timeout) {
        return new ArangoDB.Builder()
                .host(hostName, port)
                .maxConnections(maxConnections)
                .user(userName)
                .password(password)
                .timeout(timeout)
                .useSsl(false)
                .build();

    }


    @Provides
    public ArangoDatabase getDatabase(@Named("arangodb.name") String databaseName, ArangoDB arangoDB) {
        return arangoDB.db(databaseName);
    }
}
