package com.dataart.cerebro.controller;

import com.dataart.cerebro.exception.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.Serializable;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String MAX_SIZE;

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

        log.error("Error: {} {}", e.getMessage(), e);
        return new ErrorDTO(String.format("Max Upload Size Exceeded (%s)", MAX_SIZE));
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleNotEnoughMoneyException(NotEnoughMoneyException e){
        log.error("Error: {} {}", e.getMessage(), e);
        return new ErrorDTO("You don't have enough money to complete this order");
    }


    @ExceptionHandler({DataProcessingException.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleRuntimeException(Exception e) {
        log.error("Error: {}", e.getMessage(), e);
        return new ErrorDTO("Server error. Please contact the administrator");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String message;
    }
}

