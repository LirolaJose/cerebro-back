package com.dataart.cerebro.controller;

import com.dataart.cerebro.exception.AdvertisementNotFoundException;
import com.dataart.cerebro.exception.ContactInfoNullPointerException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AdvertisementNotFoundException.class)
    public String handleNotFoundException(HttpServletRequest request, Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "exception";
    }
    @ExceptionHandler(ContactInfoNullPointerException.class)
    public String handleNullPointerException(HttpServletRequest request, Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "contactInfoException";
    }
}

