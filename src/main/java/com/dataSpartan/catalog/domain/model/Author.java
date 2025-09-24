package com.dataSpartan.catalog.domain.model;

import lombok.Getter;

@Getter
public class Author {
    //authorId, name, surname, birthYear
    private Long authorId;
    private String name;
    private String surname;
    private int birthYear;

    public Author(Long authorId, String name, String surname, int birthYear) {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
    }
}
