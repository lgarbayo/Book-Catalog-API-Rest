package com.dataspartan.catalog.domain.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private Long id;
    @NonNull private String name; // name no puede ser null
    private String surname; // puede ser null
    private String nationality; // puede ser null

    // para formato de fechas se acepta el formato ISO 8601 (YYYY-MM-DD) (./resources/application.properties)
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String biography; // puede ser null

    // seudonimos
    private List<String> pseudonyms = new ArrayList<>();

    // info contacto
    private List<ContactInfo> contactInfo = new ArrayList<>();
}