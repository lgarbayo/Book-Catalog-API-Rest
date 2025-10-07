package com.dataspartan.catalog.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dataspartan.catalog.domain.book.Book;
import com.dataspartan.catalog.domain.book.BookRepository;

@Repository
public class BookRepositoryImpl implements BookRepository {

    // Almacenamiento en memoria usando Map<key, value> para búsquedas eficientes por ID

    private final Map<Long, Book> books = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book findById(Long id) {
        return books.get(id);
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId++);
        }
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Book update(Long id, Book book) {
        if (books.containsKey(id)) {
            book.setId(id); // Asegurar que mantiene el ID correcto
            books.put(id, book);
            return book;
        }
        return null; // No encontrado
    }

    @Override
    public boolean deleteById(Long id) {
        return books.remove(id) != null;
    }

    @Override
    public List<Book> findByAuthorsId(Long authorId) {
        if (authorId == null) {
            return new ArrayList<>();
        }

        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthorIds().contains(authorId)) {
                result.add(book);
            }
        }

        return result;
    }
}
