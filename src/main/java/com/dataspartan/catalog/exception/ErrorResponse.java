// Estructura del mensaje de error para respuestas de la API
package com.dataspartan.catalog.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
/*
 * Lets you construct complex objects step by step.
 * The pattern allows you to produce different types and representations of an object using the same construction code.
 */
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse { // para estandarizar la estructura de los mensajes de error en las respuestas de la API REST (con el mismo formato JSON)
    
    private int status;           // código de estado HTTP
    private String error;         // tipo/nombre del error
    private String message;       // explicación legible
    private String path;          // endpoint donde ocurrió (opcional)
    private LocalDateTime timestamp; // cuándo ocurrió (opcional)
    
}