package com.capella.mongodb.service.guice.modules;

import com.capella.mongodb.service.MongoDBService;
import com.capella.mongodb.service.MongoDBServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.inject.Named;
import java.util.Properties;

import static com.capella.mongodb.service.guice.modules.PropertiesProvider.getProperties;
import static com.google.inject.name.Names.bindProperties;

/**
 * @author Ramesh Rajendran
 */
public class MongodbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MongoDBService.class).to(MongoDBServiceImpl.class);
        binder().bind(Properties.class).toProvider(com.capella.mongodb.service.guice.modules.PropertiesProvider.class).in(Singleton.class);
        bindProperties(binder(), getProperties());
    }


    @Provides
    public com.mongodb.MongoClient getMongodbClient(@Named("mongodb.hostName") String hostName,
                                                       @Named("mongodb.port") Integer port,
                                                       @Named("mongodb.password") String password,
                                                       @Named("mongodb.user") String userName,
                                                       @Named("mongodb.databaseName") String databaseName) {
        MongoCredential credential = MongoCredential.createCredential(userName, databaseName, password.toCharArray());
        return new MongoClient(new ServerAddress(hostName, port));

    }

    @Provides
    public MongoCollection<Document> getDatabaseCollection(MongoClient mongoClient,
                                                           @Named("mongodb.databaseName") String databaseName,
                                                           @Named("mongodb.collectionName") String collectionName){
        return mongoClient.getDatabase(databaseName).getCollection(collectionName, Document.class);
    }


    @Provides
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }
}
