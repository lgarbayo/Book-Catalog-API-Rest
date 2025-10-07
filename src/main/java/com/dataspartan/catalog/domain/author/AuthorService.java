package com.dataspartan.catalog.domain.author;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author createAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);
    void logAuthorDetails(String prefix, Author author); // añadido para centralizar logging de detalles
}
