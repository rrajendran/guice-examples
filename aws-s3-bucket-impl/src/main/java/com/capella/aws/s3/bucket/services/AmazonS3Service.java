package com.capella.aws.s3.bucket.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;

public interface AmazonS3Service {

    /**
     * Put object in s3 bucket
     *
     * @param bucketName
     * @param key
     * @param input
     * @param metadata
     * @throws AmazonServiceException
     */
    void putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) throws AmazonServiceException;

    /**
     * Get an object from s3 bucket
     *
     * @param bucketName
     * @param key
     * @throws AmazonServiceException
     */
    S3Object getObject(String bucketName, String key) throws AmazonServiceException;


    /**
     * Delete an object from s3 bucket
     *
     * @param bucketName
     * @param key
     * @throws AmazonServiceException
     */
    void deleteObject(String bucketName, String key) throws AmazonServiceException;


    /**
     * Copy object from one bucket to another
     *
     * @param sourceBucketName
     * @param sourceKey
     * @param destinationBucketName
     * @param destinationKey
     * @return
     * @throws SdkClientException
     */
    String copyObject(String sourceBucketName,
                      String sourceKey,
                      String destinationBucketName,
                      String destinationKey)
            throws SdkClientException;
}