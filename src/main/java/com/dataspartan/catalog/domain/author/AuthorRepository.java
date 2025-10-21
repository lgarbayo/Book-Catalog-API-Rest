package com.dataspartan.catalog.domain.author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author save(Author author);
    Author update(Long id, Author author);
    boolean deleteById(Long id);
}
