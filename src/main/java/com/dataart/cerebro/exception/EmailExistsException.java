package com.dataart.cerebro.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String email) {
        super(String.format("%s exists, please, enter another email", email));
    }
}
