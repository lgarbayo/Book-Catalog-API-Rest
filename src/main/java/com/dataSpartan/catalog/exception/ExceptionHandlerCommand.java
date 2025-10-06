package com.dataSpartan.catalog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@FunctionalInterface // validates that the interface has exactly one abstract method
public interface ExceptionHandlerCommand {
    ResponseEntity<ErrorResponse> handle(CatalogException ex, WebRequest request);
}
