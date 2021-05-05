package com.dataart.cerebro.exception;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {

    public ValidationException(List<FieldError> fieldErrors) {
        // FIXME: 5/5/2021 must be properly filled
        super(fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", ")) + " must be filled");
    }
}
