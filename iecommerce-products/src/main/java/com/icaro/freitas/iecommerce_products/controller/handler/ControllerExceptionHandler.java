package com.icaro.freitas.iecommerce_products.controller.handler;

import java.time.Instant;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.icaro.freitas.iecommerce_products.dto.StandardErrorDto;
import com.icaro.freitas.iecommerce_products.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<StandardErrorDto> propertyReference(PropertyReferenceException ex, HttpServletRequest request) {
        StandardErrorDto err = new StandardErrorDto(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            "Invalid property given: " + ex.getPropertyName(),
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorDto> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        StandardErrorDto err = new StandardErrorDto(
            Instant.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            "Resource not found",
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
