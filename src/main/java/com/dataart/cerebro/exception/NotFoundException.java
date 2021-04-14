package com.dataart.cerebro.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String object, long id) {
        super(String.format("%s with ID (%d) doesn't exist", object, id));
    }
}
