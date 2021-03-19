package com.dataart.cerebro.exception;

public class ContactInfoNullPointerException  extends RuntimeException{

    public ContactInfoNullPointerException(){
        super(String.format("Some parameters are not filled"));
    }
}
