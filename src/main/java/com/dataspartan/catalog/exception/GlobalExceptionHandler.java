package com.dataspartan.catalog.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus; // para definir códigos de estado HTTP en las respuestas
import org.springframework.http.ResponseEntity; // sirve para controlar completamente la respuesta HTTP
import org.springframework.web.bind.annotation.ControllerAdvice; // permite manejar excepciones de manera global en toda la aplicación
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.context.request.WebRequest; // proporciona detalles sobre la solicitud HTTP

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Error 400 "Bad Request"
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
        
        String requestPath = request.getDescription(false).replace("uri=", "");
        log.warn("Bad request error on {}: {}", requestPath, ex.getMessage());
        log.debug("IllegalArgumentException details", ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            400,
            "Bad Request",
            ex.getMessage(),
            requestPath,
            LocalDateTime.now()
        );
            
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    // Error 404 "Not Found"
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        
        String requestPath = request.getDescription(false).replace("uri=", "");
        log.warn("Resource not found on {}: {}", requestPath, ex.getMessage());
        log.debug("ResourceNotFoundException details", ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            404,
            "Not Found",
            ex.getMessage(),
            requestPath,
            LocalDateTime.now()
        );
            
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Error 412 "Precondition Failed"
    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<ErrorResponse> handlePreconditionFailed(PreconditionFailedException ex, WebRequest request) {
        
        String requestPath = request.getDescription(false).replace("uri=", "");
        log.warn("Precondition failed on {}: {}", requestPath, ex.getMessage());
        log.debug("PreconditionFailedException details", ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            412,
            "Precondition Failed",
            ex.getMessage(),
            requestPath,
            LocalDateTime.now()
        );
            
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
    }

}
