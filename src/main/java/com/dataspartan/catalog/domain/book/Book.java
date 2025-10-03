// forma parte de la logica de negocio

package com.dataspartan.catalog.domain.book;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    //id, title, author ids list, publisher, edition, published date
    private Long id;
    @NonNull private String title;
    @NonNull private List<Long> authorIds;
    private String publisher;
    private String edition;
    private String publishedDate;
}