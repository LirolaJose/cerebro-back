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
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.Serializable;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleNotFoundException(NotFoundException e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO(e.getMessage());
    }

    @ExceptionHandler({ValidationException.class, EmailExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleExceptions(Exception e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO(e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO("Max Upload Size Exceeded");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ErrorDTO handleRuntimeException(Exception e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO("Server error. Please contact the administrator");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ErrorDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String message;
    }
}

