package com.dataart.cerebro.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String error, Exception e) {
        super(error, e);
    }
}
