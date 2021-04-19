package com.dataart.cerebro.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String s){
        super("Doesn't exist");
    }
}
