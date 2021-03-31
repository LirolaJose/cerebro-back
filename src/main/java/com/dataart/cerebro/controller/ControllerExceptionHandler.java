package com.dataart.cerebro.controller;

import com.dataart.cerebro.exception.AdvertisementNotFoundException;
import com.dataart.cerebro.exception.ContactInfoNullPointerException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE = "message";

    @ExceptionHandler(AdvertisementNotFoundException.class)
    public String handleNotFoundException(Exception e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        return "exception";
    }

    @ExceptionHandler(ContactInfoNullPointerException.class)
    public String handleNullPointerException(Exception e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        return "contactInfoException";
    }
}

