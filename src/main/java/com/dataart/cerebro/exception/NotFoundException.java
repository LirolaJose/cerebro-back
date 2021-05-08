package com.dataart.cerebro.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String object, Long id) {
        super(String.format("%s with ID (%d) doesn't exist", object, id));
    }

    public NotFoundException(String object) {
        super(object);
    }
}
