package com.dataart.cerebro.exception;

public class AdvertisementNotFoundException extends RuntimeException{

    public AdvertisementNotFoundException(Integer id){
        super(String.format("Advertisement with ID (%d) not found", id));
    }
}
