//Puerto(interface): DEFINE CÓMO INTERACTUAR CON EL DOMINIO, es decir, lo que se puede hacer desde fuera

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
}
