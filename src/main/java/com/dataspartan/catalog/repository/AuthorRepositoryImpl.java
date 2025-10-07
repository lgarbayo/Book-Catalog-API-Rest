package com.dataspartan.catalog.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dataspartan.catalog.domain.author.Author;
import com.dataspartan.catalog.domain.author.AuthorRepository;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    // Almacenamiento en memoria usando Map<key, value> para búsquedas eficientes por ID

    private final Map<Long, Author> authors = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    @Override
    public Author findById(Long id) {
        return authors.get(id);
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            author.setId(nextId++);
        }
        authors.put(author.getId(), author);
        return author;
    }

    @Override
    public Author update(Long id, Author author) {
        if (authors.containsKey(id)) {
            author.setId(id); // Asegurar que mantiene el ID correcto
            authors.put(id, author);
            return author;
        }
        return null; // No encontrado
    }

    @Override
    public boolean deleteById(Long id) {
        return authors.remove(id) != null;
    }
}