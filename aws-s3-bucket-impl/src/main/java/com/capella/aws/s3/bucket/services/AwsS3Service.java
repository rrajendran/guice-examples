package com.capella.aws.s3.bucket.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.util.List;

public interface AwsS3Service {

    /**
     * Put object in s3 bucket
     *
     * @param putObjectRequest
     * @throws AmazonServiceException
     */
    void putObject(PutObjectRequest putObjectRequest) throws AmazonServiceException;

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

    /**
     * Check bucket if exists.
     * @param destinationBucketName Bucket name
     * @param createIfNotFound Create if not found
     * @return
     */
    Bucket checkBucket(String destinationBucketName, boolean createIfNotFound);

    /**
     * List buckets
     * @return
     */
    List<Bucket> listBuckets();
}