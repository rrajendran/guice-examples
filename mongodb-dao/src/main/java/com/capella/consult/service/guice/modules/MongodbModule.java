package com.capella.consult.service.guice.modules;

import java.util.Properties;

import javax.inject.Named;

import org.bson.Document;

import com.capella.consult.service.MongoDBService;
import com.capella.consult.service.MongoDBServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;

/**
 * @author Ramesh Rajendran
 */
public class MongodbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MongoDBService.class).to(MongoDBServiceImpl.class);
        binder().bind(Properties.class).toProvider(PropertiesProvider.class).in(Singleton.class);
        Names.bindProperties(binder(), PropertiesProvider.getProperties());
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
}
