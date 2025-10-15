package com.dataspartan.catalog.domain.book;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
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
    @Embedded
    private List<Edition> editions = new ArrayList<>();
}