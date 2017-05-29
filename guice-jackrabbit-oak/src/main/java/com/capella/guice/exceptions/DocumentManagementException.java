package com.capella.guice.exceptions;

/**
 * @author Ramesh Rajendran
 */
public class DocumentManagementException extends RuntimeException {
    public DocumentManagementException(String message, Exception e) {
        super(message, e);
    }
}
