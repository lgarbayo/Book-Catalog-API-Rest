package com.dataspartan.catalog.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHOR")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;
    private String nationality;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "death_date")
    private LocalDate deathDate;

    @Column(length = 2000)
    private String biography;

    @ElementCollection
    @CollectionTable(name = "author_pseudonyms", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "pseudonym")
    private List<String> pseudonyms = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "author_contact_info", joinColumns = @JoinColumn(name = "author_id"))
    private List<ContactInfoEntity> contactInfo = new ArrayList<>();
}