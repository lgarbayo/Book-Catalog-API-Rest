package com.dataSpartan.catalog.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    //id, title, author list, publisher, edition, published date
    private Long id;
    private String title;
    private List<Author> authors;
    private String publisher;
    private String edition;
    private String publishedDate;
}
