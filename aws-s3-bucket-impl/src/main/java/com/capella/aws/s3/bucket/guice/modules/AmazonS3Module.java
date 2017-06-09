package com.capella.aws.s3.bucket.guice.modules;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.capella.aws.s3.bucket.services.AmazonS3Service;
import com.capella.aws.s3.bucket.services.AmazonS3ServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.inject.Named;
import java.util.Properties;

import static com.google.inject.name.Names.bindProperties;

/**
 * @author Ramesh Rajendran
 */
public class AmazonS3Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(AmazonS3Service.class).to(AmazonS3ServiceImpl.class);
        binder().bind(Properties.class).toProvider(PropertiesProvider.class).in(Singleton.class);
        bindProperties(binder(), PropertiesProvider.getProperties());
    }


    @Provides
    public AmazonS3 getAmazonS3Client(@Named("aws.region") String region) {

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
        Region usWest2 = Region.getRegion(Regions.valueOf(region));
        amazonS3.setRegion(usWest2);
        return amazonS3;
    }
}
