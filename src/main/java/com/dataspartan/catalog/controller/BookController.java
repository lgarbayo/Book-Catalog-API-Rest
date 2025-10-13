package com.dataspartan.catalog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataspartan.catalog.domain.book.Book;
import com.dataspartan.catalog.domain.book.BookService;
import com.dataspartan.catalog.domain.book.Edition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    // Book CRUD endpoints
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
        bookService.logBookDetails("Retrieved book", book);
        return book;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        log.info("POST /book - Creating new book with title: {}", book.getTitle());
        bookService.logBookDetails("Book creation request", book);
        Book createdBook = bookService.createBook(book);
        log.info("Successfully created book with ID: {}", createdBook.getId());
        return createdBook;
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        log.info("PUT /book/{} - Updating book", id);
        bookService.logBookDetails("Book update request", book);
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

    // Edition CRUD endpoints
    @PostMapping("/{bookId}/edition")
    public Edition addEditionToBook(@PathVariable Long bookId, @RequestBody Edition edition) {
        log.info("POST /book/{}/editions - Adding edition to book", bookId);
        return bookService.addEditionToBook(bookId, edition);
    }

    @GetMapping("/{bookId}/edition")
    public List<Edition> getAllEditionsFromBook(@PathVariable Long bookId) {
        log.info("GET /book/{}/editions - Retrieving all editions from book", bookId);
        return bookService.getAllEditionsFromBook(bookId);
    }

    @GetMapping("/{bookId}/edition/{editionIndex}")
    public Edition getEdition(@PathVariable Long bookId, @PathVariable int editionIndex) {
        log.info("GET /book/{}/editions/{} - Retrieving edition", bookId, editionIndex);
        return bookService.getEdition(bookId, editionIndex);
    }

    @PutMapping("/{bookId}/edition/{editionIndex}")
    public Edition updateEdition(@PathVariable Long bookId, @PathVariable int editionIndex, @RequestBody Edition edition) {
        log.info("PUT /book/{}/editions/{} - Updating edition", bookId, editionIndex);
        return bookService.updateEdition(bookId, editionIndex, edition);
    }

    @DeleteMapping("/{bookId}/edition/{editionIndex}")
    public void deleteEdition(@PathVariable Long bookId, @PathVariable int editionIndex) {
        log.info("DELETE /book/{}/editions/{} - Deleting edition", bookId, editionIndex);
        bookService.deleteEdition(bookId, editionIndex);
    }
}