package com.ecommerce.productservice.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handleIllegalAccessException(IllegalAccessException accessException) {
        return new ResponseEntity<>(
          "Error accessing fields. Reason : " + accessException.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }
}
