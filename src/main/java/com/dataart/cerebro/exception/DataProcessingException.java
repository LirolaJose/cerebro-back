package com.dataart.cerebro.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(Exception e) {
        super(String.format("Data processing exception: %s", e.getMessage()));
    }
}
