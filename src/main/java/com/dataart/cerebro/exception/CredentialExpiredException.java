package com.dataart.cerebro.exception;

public class CredentialExpiredException extends RuntimeException{
    public CredentialExpiredException(String error) {
        super(error);
    }
}
