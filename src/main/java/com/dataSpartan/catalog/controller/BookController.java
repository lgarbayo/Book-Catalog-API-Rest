package com.dataSpartan.catalog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataSpartan.catalog.domain.book.Book;
import com.dataSpartan.catalog.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    // Endpoints
    @GetMapping
    public List<Book> getAllBooks() {
        log.info("GET /book - Retrieving all books");
        List<Book> books = bookService.getAllBooks();
        log.debug("Retrieved {} books", books.size());
        return books;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        log.info("GET /book/{} - Retrieving book by ID", id);
        Book book = bookService.getBookById(id);
        log.debug("Retrieved book: title: {}, publisher: {}", book.getTitle(), book.getPublisher());
        return book;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        log.info("POST /book - Creating new book with title: {}", book.getTitle());
        log.debug("Book creation request: publisher: {}, edition: {}, authors count: {}", 
                 book.getPublisher(), book.getEdition(), 
                 book.getAuthors() != null ? book.getAuthors().size() : 0);
        Book createdBook = bookService.createBook(book);
        log.info("Successfully created book with ID: {}", createdBook.getId());
        return createdBook;
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        log.info("PUT /book/{} - Updating book", id);
        log.debug("Book update request: title: {}, publisher: {}", book.getTitle(), book.getPublisher());
        Book updatedBook = bookService.updateBook(id, book);
        log.info("Successfully updated book with ID: {}", id);
        return updatedBook;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        log.info("DELETE /book/{} - Deleting book", id);
        bookService.deleteBook(id);
        log.info("Successfully deleted book with ID: {}", id);
    }
    
}