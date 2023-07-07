package com.example.ordermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class OrderExceptionHandler {

    // Exception handler for OrderNotFoundException
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        String errorMessage = ex.getMessage();
        CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage, List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Exception handler for BindException
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomErrorResponse> handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        return fieldError.getField() + ": " + error.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        CustomErrorResponse errorResponse = new CustomErrorResponse("Validation Error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Exception handler for generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        CustomErrorResponse errorResponse = new CustomErrorResponse("Internal Server Error", errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
