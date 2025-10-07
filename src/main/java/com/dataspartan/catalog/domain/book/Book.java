package com.dataspartan.catalog.domain.book;

import java.time.LocalDate;
import java.util.List;

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
    private String publisher;
    private String edition;
    private LocalDate publishedDate;
    public String genre;
    private Integer pages;
    private String country;
    private String language;
    private String isbn; // p.ej.: xxx-xx-xxxxx-xx-x
    private String description;
    private Integer totalCopies;
    private Integer soldCopies;
}