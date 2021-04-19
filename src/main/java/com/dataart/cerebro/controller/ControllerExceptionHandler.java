package com.dataart.cerebro.controller;

import com.dataart.cerebro.exception.EntityNotFoundException;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    private static final String MESSAGE = "message";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleNotFoundException(NotFoundException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(Exception e, Model model) {
        model.addAttribute(MESSAGE, "Validation exception: " + e.getMessage());
        model.addAttribute("title", "Fill the all required fields");
        return "exception";
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<?> handleRuntimeException(Exception e) {

        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleEntityNotFoundException(Exception e) {
        return new ResponseEntity<>("Your request is not found:", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ErrorDto implements Serializable {
        private static final long serialVersionUID = 1L;
        private String message;
    }
}

