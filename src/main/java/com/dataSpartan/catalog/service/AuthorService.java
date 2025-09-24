//Puerto(interface): DEFINE CÓMO INTERACTUAR CON EL DOMINIO, es decir, lo que se puede hacer desde fuera

package com.dataSpartan.catalog.service;

import java.util.List;
import com.dataSpartan.catalog.domain.model.Author;

public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author createAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);
}
