package com.dataart.cerebro.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String error, Exception e) {
        super(String.format("%s, %s", error, e.getMessage()));
    }

    public DataProcessingException(String error) {
        super(error);
    }
}
