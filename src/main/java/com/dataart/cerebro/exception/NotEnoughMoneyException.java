package com.dataart.cerebro.exception;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(String error) {
        super(error);
    }
}
