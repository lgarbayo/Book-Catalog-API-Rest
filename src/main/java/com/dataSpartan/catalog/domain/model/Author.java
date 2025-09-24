package com.dataSpartan.catalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author {
    //id, name, surname, birthYear
    private Long id;
    private String name;
    private String surname;
    private int birthYear;
}
