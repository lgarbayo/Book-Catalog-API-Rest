package com.dataspartan.catalog.domain.author;

import jakarta.persistence.*;
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
@Entity
@Table(name = "AUTHOR")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name; // name no puede ser null

    private String surname; // puede ser null
    private String nationality; // puede ser null

    // para formato de fechas se acepta el formato ISO 8601 (YYYY-MM-DD) (./resources/application.properties)
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "death_date")
    private LocalDate deathDate;

    @Column(length = 2000)
    private String biography; // puede ser null

    // seudonimos
    @ElementCollection
    @CollectionTable(name = "author_pseudonyms", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "pseudonym")
    private List<String> pseudonyms = new ArrayList<>();

    // info contacto
    @ElementCollection
    @CollectionTable(name = "author_contact_info", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "contact_info")
    private List<ContactInfo> contactInfo = new ArrayList<>();
}