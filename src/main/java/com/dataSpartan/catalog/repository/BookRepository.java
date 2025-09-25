package com.dataSpartan.catalog.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dataSpartan.catalog.domain.model.Book;

@Repository
public class BookRepository {

    // Almacenamiento en memoria usando Map<key, value> para búsquedas eficientes por ID
    
    private final Map<Long, Book> books = new HashMap<>();
    private Long nextId = 1L;

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Book findById(Long id) {
        return books.get(id);
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId++);
        }
        books.put(book.getId(), book);
        return book;
    }

    public Book update(Long id, Book book) {
        if (books.containsKey(id)) {
            book.setId(id); // Asegurar que mantiene el ID correcto
            books.put(id, book);
            return book;
        }
        return null; // No encontrado
    }

    public boolean deleteById(Long id) {
        return books.remove(id) != null;
    }

}
