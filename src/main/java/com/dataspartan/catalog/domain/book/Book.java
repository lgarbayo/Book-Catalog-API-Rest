// forma parte de la logica de negocio

package com.dataspartan.catalog.domain.book;

import java.util.List;

import com.dataspartan.catalog.domain.author.Author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    //id, title, author list, publisher, edition, published date
    private Long id;
    @NonNull private String title;
    @NonNull private List<Author> authors;
    private String publisher;
    private String edition;
    private String publishedDate;
}