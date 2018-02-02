package com.capella.aws.s3.bucket.exceptions;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ramesh Rajendran
 */
@Data
@Builder
public class ServiceException extends Exception{
    private String errorCode;
    private String errorMessage;
    private String errorType;
    private String requestId;
    private String serviceName;
    private int statusCode;

}
