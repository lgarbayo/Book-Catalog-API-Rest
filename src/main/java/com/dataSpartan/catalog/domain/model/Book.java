package com.dataSpartan.catalog.domain.model;

import java.util.List;

import lombok.Getter;

@Getter
public class Book {
    //bookId, title, author list, publisher, edition, published date
    private Long bookId;
    private String title;
    private List<Author> authors;
    private String publisher;
    private String edition;
    private String publishedDate;

    public Book(Long bookId, String title, List<Author> authors, String publisher, String edition, String publishedDate) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.edition = edition;
        this.publishedDate = publishedDate;
    }
}
