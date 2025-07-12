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
		final HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardErrorDto err = new StandardErrorDto(
            Instant.now(),
            status.value(),
            "Bad Request",
            "Invalid property given: " + ex.getPropertyName(),
            request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorDto> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		final HttpStatus status = HttpStatus.NOT_FOUND;
        StandardErrorDto err = new StandardErrorDto(
            Instant.now(),
            status.value(),
            "Not Found",
            "Resource not found",
            request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

}
