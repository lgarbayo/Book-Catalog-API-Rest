package com.dataspartan.catalog.domain.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Edition {
    private String isbn;
    private String publisher;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    private String country;
    private String language;
    private Integer pages;

    @Column(name = "total_copies")
    private Integer totalCopies;

    @Column(name = "sold_copies")
    private Integer soldCopies;

    @Column(length = 1000)
    private String description;
}
