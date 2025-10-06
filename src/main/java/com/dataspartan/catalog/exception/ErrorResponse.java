package com.dataspartan.catalog.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private int status;           // código de estado HTTP
    private String error;         // tipo/nombre del error
    private String message;       // explicación legible
    private String path;          // endpoint donde ocurrió (opcional)
    private LocalDateTime timestamp; // cuándo ocurrió (opcional)
}
