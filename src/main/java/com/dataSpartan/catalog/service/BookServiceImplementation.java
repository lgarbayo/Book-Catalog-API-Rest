package com.dataSpartan.catalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dataSpartan.catalog.domain.model.Book;
import com.dataSpartan.catalog.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id);
        // Verificar que el libro existe
        if (book == null) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }
        return book;
    }

    @Override
    public Book createBook(Book book) {
        // Verificar que el libro no es nulo y tiene título
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title is required");
        }
        
        // Asegurar que es un libro nuevo (sin ID)
        book.setId(null);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        // Verificar que el libro existe
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }

        // Vaerificar que el libro no es nulo y tiene título
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title is required");
        }

        // Update el libro en el repositorio
        return bookRepository.update(id, book);
    }

    @Override
    public void deleteBook(Long id) {
        // Verificar que el libro existe antes de intentar eliminarlo
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }

        boolean deleted = bookRepository.deleteById(id);
        // Verificar que realmente se ha eliminado
        if (!deleted) {
            throw new IllegalArgumentException("Failed to delete book with id: " + id);
        }
    }
}
