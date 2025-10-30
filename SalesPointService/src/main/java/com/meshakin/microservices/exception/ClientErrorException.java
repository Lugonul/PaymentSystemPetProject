package com.meshakin.microservices.exception;

public class ClientErrorException extends RemoteServiceException {
    public ClientErrorException(String message) {
        super(message);
    }
}