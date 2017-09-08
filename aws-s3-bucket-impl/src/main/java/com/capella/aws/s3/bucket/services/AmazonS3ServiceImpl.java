package com.capella.aws.s3.bucket.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import javax.inject.Inject;

/**
 * @author Ramesh Rajendran
 */
public class AmazonS3ServiceImpl implements AmazonS3Service {

    @Inject
    private AmazonS3 amazonS3;

    @Override
    public void putObject(PutObjectRequest putObjectRequest) throws AmazonServiceException {
        if(!amazonS3.doesBucketExist(putObjectRequest.getBucketName())){
            amazonS3.createBucket(putObjectRequest.getBucketName());
        }
        amazonS3.putObject( putObjectRequest);
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

        if(!amazonS3.doesBucketExist(destinationBucketName)){
            amazonS3.createBucket(destinationBucketName);
        }

        CopyObjectResult copyObjectResult = amazonS3.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
        return copyObjectResult.getETag();
    }
}
