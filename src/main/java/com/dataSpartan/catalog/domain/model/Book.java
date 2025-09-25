package com.dataSpartan.catalog.domain.model;

import java.util.List;

public class Book {
    //id, title, author list, publisher, edition, published date
    private Long id;
    private String title;
    private List<Author> authors;
    private String publisher;
    private String edition;
    private String publishedDate;

    // Constructors
    public Book() {}

    public Book(Long id, String title, List<Author> authors, String publisher, String edition, String publishedDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.edition = edition;
        this.publishedDate = publishedDate;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getEdition() {
        return edition;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
}
