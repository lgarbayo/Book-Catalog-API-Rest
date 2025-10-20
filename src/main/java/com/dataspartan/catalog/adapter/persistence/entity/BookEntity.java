package com.dataspartan.catalog.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "book_author_ids", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "author_id", nullable = false)
    private List<Long> authorIds = new ArrayList<>();

    @Column(name = "publication_year")
    private Integer publicationYear; // fecha de publicación original del libro

    @ElementCollection
    @CollectionTable(name = "book_author_pseudonyms", joinColumns = @JoinColumn(name = "book_id"))
    @MapKeyColumn(name = "author_id")
    @Column(name = "pseudonym")
    private Map<Long, String> authorPseudonyms = new HashMap<>();

    // lista de ediciones del libro
    @ElementCollection
    @CollectionTable(name = "book_editions", joinColumns = @JoinColumn(name = "book_id"))
    private List<EditionEntity> editions = new ArrayList<>();
}