package com.dataspartan.catalog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final ExceptionHandlerRegistry registry;

    public GlobalExceptionHandler(ExceptionHandlerRegistry registry) {
        this.registry = registry;
    }

    @ExceptionHandler(CatalogException.class)
    public ResponseEntity<ErrorResponse> handleCatalogException(CatalogException ex, WebRequest request) {
        log.info("Handling exception: {} for request: {}",
                ex.getClass().getSimpleName(),
                request.getDescription(false));

        return registry.executeCommand(ex, request);
    }
}
