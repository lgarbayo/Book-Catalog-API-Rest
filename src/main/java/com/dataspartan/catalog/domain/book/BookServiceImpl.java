package com.dataspartan.catalog.domain.book;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dataspartan.catalog.exception.ResourceNotFoundException;
import com.dataspartan.catalog.repository.BookRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        log.debug("Fetching all books. Total count: {}", bookRepository.findAll().size());
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(@NonNull Long id) {
        log.debug("Fetching book with ID: {}", id);
        Book book = bookRepository.findById(id);
        if (book == null) {
            log.warn("Book not found with ID: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        log.debug("Book found: {}", book.getTitle());
        return book;
    }

    @Override
    public Book createBook(@NonNull Book book) {
        log.info("Creating new book with title: {}", book.getTitle());
        log.debug("Book details: authors count: {}, publisher: {}", 
                  book.getAuthors() != null ? book.getAuthors().size() : 0, book.getPublisher());
        
        // Validacion del título
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) { // Validacion necesaria ya que @NonNull no lo garantiza en deserialización JSON
            log.warn("Attempt to create book with null or empty title");
            // sin la validacion de == null salta error 500
            throw new IllegalArgumentException("Title is required and cannot be empty");
        }
        
        // Validacion de autores
        if (book.getAuthors() == null || book.getAuthors().isEmpty()) { // Validacion necesaria ya que @NonNull no lo garantiza en deserialización JSON
            log.warn("Attempt to create book without authors");
            // sin la validacion de == null salta error 500
            throw new IllegalArgumentException("At least one author is required");
        }
        
        // Asegurar que es un libro nuevo (sin ID)
        book.setId(null);
        log.info("Book created successfully with ID: {}", bookRepository.save(book).getId());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(@NonNull Long id, @NonNull Book book) {
        log.info("Updating book with ID: {}", id);
        log.debug("Updated book details: title: {}, authors count: {}", 
                  book.getTitle(), book.getAuthors() != null ? book.getAuthors().size() : 0);
        
        // Verificar que el libro existe
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            log.warn("Cannot update - Book not found with ID: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }

        // Validacion del título
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) { // Validacion necesaria ya que @NonNull no lo garantiza en deserialización JSON
            log.warn("Attempt to update book with null or empty title");
            // sin la validacion de == null salta error 500
            throw new IllegalArgumentException("Title is required and cannot be empty");
        }
        
        // Validacion de autores
        if (book.getAuthors() == null || book.getAuthors().isEmpty()) { // Validacion necesaria ya que @NonNull no lo garantiza en deserialización JSON
            log.warn("Attempt to update book without authors");
            // sin la validacion de == null salta error 500
            throw new IllegalArgumentException("At least one author is required");
        }

        // Update el libro en el repositorio
        log.info("Book updated successfully with ID: {}", id);
        return bookRepository.update(id, book);
    }

    @Override
    public void deleteBook(@NonNull Long id) {
        log.info("Deleting book with ID: {}", id);
        
        // Verificar que el libro existe antes de intentar eliminarlo
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            log.warn("Cannot delete - Book not found with ID: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }

        boolean deleted = bookRepository.deleteById(id);
        if (!deleted) {
            log.error("Failed to delete book with ID: {}", id);
            throw new RuntimeException("Failed to delete book with id: " + id);
        }
        
        log.info("Book deleted successfully with ID: {}", id);
    }
}
