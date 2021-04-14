package com.dataart.cerebro.controller;

import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE = "message";

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(Exception e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        model.addAttribute("title", "Your request is not found:");
        return "exception";
    }

    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(Exception e, Model model) {
        model.addAttribute(MESSAGE, "Validation exception: " + e.getMessage());
        model.addAttribute("title", "Fill the all required fields");
        return "exception";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(Exception e, Model model) {
        model.addAttribute(MESSAGE, "Unexpected exception");
        model.addAttribute("title", "Something wrong:");
        log.error("Exception message: " + e.getMessage(), e);
        return "exception";
    }
}

