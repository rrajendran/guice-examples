package com.capella.aws.s3.bucket.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Ramesh Rajendran
 */
public class AwsS3ServiceImpl implements AwsS3Service {

    @Inject
    private AmazonS3 amazonS3;

    @Override
    public void putObject(PutObjectRequest putObjectRequest) throws AmazonServiceException {
        checkBucket(putObjectRequest.getBucketName(), true);
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

        checkBucket(destinationBucketName, true);

        CopyObjectResult copyObjectResult = amazonS3.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
        return copyObjectResult.getETag();
    }

    @Override
    public Bucket checkBucket(String destinationBucketName, boolean createIfNotFound) {
        if(createIfNotFound && !amazonS3.doesBucketExist(destinationBucketName)){
            return amazonS3.createBucket(destinationBucketName);
        }
        return null;
    }

    @Override
    public List<Bucket> listBuckets(){
        return amazonS3.listBuckets();
    }
}
