package com.dataart.cerebro.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundIdException extends Exception{
    @Override
    public String toString(){
        log.error("This Id doesn't exist. Try again");
        return "This Id doesn't exist. Try again";
    }
}
