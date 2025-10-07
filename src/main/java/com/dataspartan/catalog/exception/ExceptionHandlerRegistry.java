package com.dataspartan.catalog.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExceptionHandlerRegistry {

    private final Map<Class<? extends CatalogException>, ExceptionHandlerCommand> handlers = new HashMap<>();

    public ExceptionHandlerRegistry() {
        registerHandlers();
    }

    private void registerHandlers() {
        handlers.put(InvalidArgumentsException.class, (ex, request) ->
            buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex, request));
        handlers.put(ResourceNotFoundException.class, (ex, request) ->
            buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex, request));
        handlers.put(PreconditionFailedException.class, (ex, request) ->
            buildResponse(HttpStatus.PRECONDITION_FAILED, "Precondition Failed", ex, request));
    }

    public ResponseEntity<ErrorResponse> executeCommand(CatalogException ex, WebRequest request) {
        ExceptionHandlerCommand command = handlers.get(ex.getClass());

        if (command == null) {
            log.error("No handler registered for exception type: {}", ex.getClass().getSimpleName());
            return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex, request);
        }

        return command.handle(ex, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status, String error, CatalogException ex, WebRequest request) {

        String requestPath = request.getDescription(false).replace("uri=", "");

        log.warn("{} on {}: {}", ex.getClass().getSimpleName(), requestPath, ex.getMessage());
        log.debug("Exception details", ex);

        ErrorResponse response = new ErrorResponse(
                status.value(),
                error,
                ex.getMessage(),
                requestPath,
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(response);
    }
}
