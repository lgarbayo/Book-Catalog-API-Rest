package com.dataspartan.catalog.domain.book;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book createBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
    List<Book> findBooksByAuthorId(Long authorId);
    void logBookDetails(String prefix, Book book);

    // CRUD operations for editions
    Edition addEditionToBook(Long bookId, Edition edition);
    Edition updateEdition(Long bookId, int editionIndex, Edition edition);
    void deleteEdition(Long bookId, int editionIndex);
    Edition getEdition(Long bookId, int editionIndex);
    List<Edition> getAllEditionsFromBook(Long bookId);
}
