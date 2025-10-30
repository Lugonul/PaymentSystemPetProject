package com.meshakin.microservices.exception;

public class EmptyResponseException extends RetryableRemoteServiceException {
    public EmptyResponseException(String message) {
        super(message);
    }
}