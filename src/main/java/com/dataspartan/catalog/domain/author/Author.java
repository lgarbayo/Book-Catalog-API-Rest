// forma parte de la logica de negocio

package com.dataspartan.catalog.domain.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    //id, name, surname, birthYear
    private Long id;
    @NonNull private String name;
    private String surname;
    private int birthYear;
}