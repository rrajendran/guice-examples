package com.capella.aws.s3.bucket.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import javax.inject.Inject;
import java.io.InputStream;

/**
 * @author Ramesh Rajendran
 */
public class AmazonS3ServiceImpl implements AmazonS3Service {

    @Inject
    private AmazonS3 amazonS3;

    @Override
    public void putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) throws AmazonServiceException {
        amazonS3.putObject(bucketName, key, input, metadata);
    }

    @Override
    public S3Object getObject(String bucketName, String key) throws AmazonServiceException {
        return amazonS3.getObject(bucketName, key);
    }

    @Override
    public void deleteObject(String bucketName, String key) throws AmazonServiceException {
        amazonS3.deleteObject(bucketName, key);
    }

    @Override
    public String copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) throws SdkClientException {
        CopyObjectResult copyObjectResult = amazonS3.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
        return copyObjectResult.getETag();
    }
}
