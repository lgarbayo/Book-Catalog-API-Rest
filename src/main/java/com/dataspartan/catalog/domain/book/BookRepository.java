package com.dataspartan.catalog.domain.book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    Book update(Long id, Book book);
    boolean deleteById(Long id);
    List<Book> findByAuthorsId(Long authorId);
}
