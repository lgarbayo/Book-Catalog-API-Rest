package com.dataspartan.catalog.domain.book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    @NonNull private String title;
    @NonNull private List<Long> authorIds;
    private Integer publicationYear; // fecha de publicación original del libro

    private Map<Long, String> authorPseudonyms = new HashMap<>();

    // lista de ediciones del libro
    private List<Edition> editions = new ArrayList<>();
}