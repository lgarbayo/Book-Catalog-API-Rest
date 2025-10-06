package com.dataspartan.catalog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

/**
 * Interfaz funcional que define un comando para manejar excepciones específicas.
 */
@FunctionalInterface
public interface ExceptionHandlerCommand {
    ResponseEntity<ErrorResponse> handle(CatalogException ex, WebRequest request);
}
