package com.capella.mongodb.service.guice.modules;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.capella.mongodb.service.ArangodbService;
import com.capella.mongodb.service.ArangodbServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.inject.Named;
import java.util.Properties;

import static com.google.inject.name.Names.bindProperties;

/**
 * @author Ramesh Rajendran
 */
public class MongodbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ArangodbService.class).to(ArangodbServiceImpl.class);
        binder().bind(Properties.class).toProvider(com.capella.mongodb.service.guice.modules.PropertiesProvider.class).in(Singleton.class);
        bindProperties(binder(), com.capella.mongodb.service.guice.modules.PropertiesProvider.getProperties());
    }


    @Provides
    public com.mongodb.MongoClient getArangodbInstance(@javax.inject.Named("arangodb.hosts") String hostName, @javax.inject.Named("arangodb.port") String port,
                                                       @javax.inject.Named("arangodb.connections.max") Integer maxConnections, @javax.inject.Named("arangodb.password") String password,
                                                       @javax.inject.Named("arangodb.user") String userName, @Named("arangodb.name") String databaseName,
                                                       @javax.inject.Named("arangodb.timeout") Integer timeout) {
        com.mongodb.MongoClientOptions mongoOptions = null;

        com.mongodb.MongoCredential mongoCredential = com.mongodb.MongoCredential.createCredential(userName, databaseName,
                password.toCharArray());
        com.mongodb.MongoClientOptions options = com.mongodb.MongoClientOptions.builder().connectTimeout(timeout).build();
        return new com.mongodb.MongoClient("hostName", port, mongoCredential, options);

    }


    @Provides
    public ArangoDatabase getDatabase(, ArangoDB arangoDB) {
        return arangoDB.db(databaseName);
    }
}
