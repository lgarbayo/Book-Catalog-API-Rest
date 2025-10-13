package com.dataspartan.catalog.domain.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edition {
    private String isbn;
    private String publisher;
    private LocalDate publishedDate;
    private String country;
    private String language;
    private Integer pages;
    private Integer totalCopies;
    private Integer soldCopies;
    private String description;
}
