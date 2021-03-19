package com.dataart.cerebro.exception;

public class ContactInfoNullPointerException  extends RuntimeException{

    public ContactInfoNullPointerException(){
        super(String.format("Phone and email fields must be filled"));
    }
}
