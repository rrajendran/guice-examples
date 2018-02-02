package com.capella.aws.s3.bucket.guice.modules;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.capella.aws.s3.bucket.services.AwsS3Service;
import com.capella.aws.s3.bucket.services.AwsS3ServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.inject.Named;
import java.util.Properties;

import static com.google.inject.name.Names.bindProperties;

/**
 * @author Ramesh Rajendran
 */
public class AwsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AwsS3Service.class).to(AwsS3ServiceImpl.class);
        binder().bind(Properties.class).toProvider(PropertiesProvider.class).in(Singleton.class);
        bindProperties(binder(), PropertiesProvider.getProperties());
    }


    @Provides
    public AmazonS3 getAmazonS3Client(@Named("aws.url") String url, @Named("aws.region") String region,
                                      @Named("aws.accessKey") String accessKey, @Named("aws.secretKey") String secretKey) {
        EndpointConfiguration endpoint = new EndpointConfiguration(url, region);
        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
                .build();

    }


    @Provides
    public AmazonSQS getAmazonSQSClient(@Named("aws.region") String region,
                                        @Named("aws.accessKey") String accessKey, @Named("aws.secretKey") String secretKey) {
        return AmazonSQSClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
                .build();

    }
}
