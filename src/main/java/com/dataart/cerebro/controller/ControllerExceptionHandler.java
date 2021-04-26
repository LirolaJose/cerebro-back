package com.dataart.cerebro.controller;

import com.dataart.cerebro.exception.EmailExistsException;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ErrorDTO handleNotFoundException(NotFoundException e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleValidationException(ValidationException e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO(e.getMessage());
    }

    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handlerEmailExistsException(EmailExistsException e){
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO((e.getMessage()));
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<?> handleRuntimeException(Exception e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ResponseEntity<>("Server error. Please contact the administrator", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ErrorDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String message;
    }
}

