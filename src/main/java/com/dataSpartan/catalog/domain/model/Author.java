// forma parte de la logica de negocio

package com.dataSpartan.catalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    //id, name, surname, birthYear
    private Long id;
    private String name;
    private String surname;
    private int birthYear;
}
