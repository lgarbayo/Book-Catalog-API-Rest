package com.dataspartan.catalog.domain.book;

import java.util.List;

import com.dataspartan.catalog.exception.InvalidArgumentsException;
import com.dataspartan.catalog.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

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
        log.info("Fetching book with ID: {}", id);
        Book book = bookRepository.findById(id);
        if (book == null) {
            log.warn("Book not found with ID: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        logBookDetails("Book found", book);
        return book;
    }

    @Override
    public Book createBook(@NonNull Book book) {
        log.info("Creating new book with title: {}", book.getTitle());
        logBookDetails("Book details", book);
        validateBook(book);
        book.setId(null);
        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with ID: {}", savedBook.getId());
        logBookDetails("Created book", savedBook);
        return savedBook;
    }

    @Override
    public Book updateBook(@NonNull Long id, @NonNull Book book) {
        log.info("Updating book with ID: {}", id);
        logBookDetails("Update request details", book);
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            log.warn("Cannot update - Book not found with ID: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        validateBook(book);
        Book updatedBook = bookRepository.update(id, book);
        log.info("Book updated successfully with ID: {}", id);
        logBookDetails("Updated book details", updatedBook);
        return updatedBook;
    }

    @Override
    public void deleteBook(@NonNull Long id) {
        log.info("Deleting book with ID: {}", id);
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

    @Override
    public List<Book> findBooksByAuthorId(Long authorId) {
        log.info("Finding books by author ID: {}", authorId);
        List<Book> books = bookRepository.findByAuthorsId(authorId);
        log.debug("Found {} books for author ID: {}", books.size(), authorId);
        return books;
    }

    private void validateBook(Book book) {

        // Validacion titulo
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            log.warn("Attempt to create/update book with null or empty title");
            throw new InvalidArgumentsException("Title is required and cannot be empty");
        }

        // Validacion autor(es)
        if (book.getAuthorIds() == null || book.getAuthorIds().isEmpty()) {
            log.warn("Attempt to create/update book without authors");
            throw new InvalidArgumentsException("At least one author is required");
        }

        // Validacion formato isbn
        if (book.getIsbn() != null && !book.getIsbn().trim().isEmpty()) {
            String isbn = book.getIsbn().replaceAll("[^0-9X]", "");
            if (isbn.length() != 10 && isbn.length() != 13) {
                log.warn("Invalid ISBN format: {}", book.getIsbn());
                throw new InvalidArgumentsException("ISBN must be 10 or 13 digits");
            }
        }

        // Validaciones con numeros de copias
        if (book.getTotalCopies() != null && book.getTotalCopies() < 0) {
            log.warn("Invalid total copies: {}", book.getTotalCopies());
            throw new InvalidArgumentsException("Total copies cannot be negative");
        }
        if (book.getSoldCopies() != null && book.getSoldCopies() < 0) {
            log.warn("Invalid sold copies: {}", book.getSoldCopies());
            throw new InvalidArgumentsException("Sold copies cannot be negative");
        }
        if (book.getTotalCopies() != null && book.getSoldCopies() != null && book.getSoldCopies() > book.getTotalCopies()) {
            log.warn("Sold copies ({}) exceed total copies ({})", book.getSoldCopies(), book.getTotalCopies());
            throw new InvalidArgumentsException("Sold copies cannot exceed total copies");
        }

        // Validacion fecha de publicación
        if (book.getPublishedDate() != null && book.getPublishedDate().isAfter(java.time.LocalDate.now())) {
            log.warn("Published date is in the future for book: {}", book.getTitle());
            throw new InvalidArgumentsException("Published date cannot be in the future");
        }
    }

    public void logBookDetails(String prefix, Book book) {
        if (log.isDebugEnabled()) {
            log.debug("{}: title: {}, authorIds: {}, publisher: {}, edition: {}, publishedDate: {}, genre: {}, pages: {}, country: {}, language: {}, isbn: {}, description length: {}, totalCopies: {}, soldCopies: {}",
                prefix,
                book.getTitle(),
                book.getAuthorIds(),
                book.getPublisher() != null ? book.getPublisher() : "No publisher",
                book.getEdition() != null ? book.getEdition() : "No edition",
                book.getPublishedDate() != null ? book.getPublishedDate() : "Unknown published date",
                book.getGenre() != null ? book.getGenre() : "Unknown genre",
                book.getPages() != null ? book.getPages() : 0,
                book.getCountry() != null ? book.getCountry() : "Unknown country",
                book.getLanguage() != null ? book.getLanguage() : "Unknown language",
                book.getIsbn() != null ? book.getIsbn() : "No ISBN",
                book.getDescription() != null ? book.getDescription() : "No description",
                book.getTotalCopies() != null ? book.getTotalCopies() : 0,
                book.getSoldCopies() != null ? book.getSoldCopies() : 0);
        }
    }
}
