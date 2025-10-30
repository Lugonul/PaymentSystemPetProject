package com.meshakin.microservices.exception;

public class ServerErrorException extends RetryableRemoteServiceException {
    public ServerErrorException(String message) {
        super(message);
    }
}