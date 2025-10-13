package com.dataspartan.catalog.domain.book;

import java.util.ArrayList;
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

    // Edition
    @Override
    public Edition addEditionToBook(@NonNull Long bookId, @NonNull Edition edition) {
        log.info("Adding edition to book with ID: {}", bookId);
        Book book = getBookById(bookId);

        if (book.getEditions() == null) {
            book.setEditions(new ArrayList<>());
        }

        book.getEditions().add(edition);
        bookRepository.update(bookId, book);
        log.info("Successfully added edition at index {} to book: {}", bookId);
        return edition;
    }

    @Override
    public Edition updateEdition(@NonNull Long bookId, int editionIndex, @NonNull Edition edition) {
        log.info("Updating edition at index {} in book {}", editionIndex, bookId);
        Book book = getBookById(bookId);

        if (book.getEditions() == null) {
            log.warn("No editions found for book {}", bookId);
            throw new ResourceNotFoundException("No editions exist for book with ID: " + bookId);
        } else if (editionIndex < 0) {
            log.warn("Negative edition index ({}) for book {}", editionIndex, bookId);
            throw new InvalidArgumentsException("Edition index cannot be negative: " + editionIndex);
        } else if (editionIndex >= book.getEditions().size()) {
            log.warn("Edition index out of range ({}) for book {}", editionIndex, bookId);
            throw new InvalidArgumentsException("No edition found at index: " + editionIndex + " for book with ID: " + bookId);
        }

        book.getEditions().set(editionIndex, edition);
        bookRepository.update(bookId, book);
        log.info("Successfully updated edition at index: {}", editionIndex);
        return edition;
    }

    @Override
    public void deleteEdition(@NonNull Long bookId, int editionIndex) {
        log.info("Deleting edition at index {} from book {}", editionIndex, bookId);
        Book book = getBookById(bookId);

        if (book.getEditions() == null) {
            log.warn("No editions found for book {}", bookId);
            throw new ResourceNotFoundException("No editions exist for book with ID: " + bookId);
        } else if (editionIndex < 0) {
            log.warn("Negative edition index ({}) for book {}", editionIndex, bookId);
            throw new InvalidArgumentsException("Edition index cannot be negative: " + editionIndex);
        } else if (editionIndex >= book.getEditions().size()) {
            log.warn("Edition index out of range ({}) for book {}", editionIndex, bookId);
            throw new InvalidArgumentsException("No edition found at index: " + editionIndex + " for book with ID: " + bookId);
        }

        book.getEditions().remove(editionIndex);
        bookRepository.update(bookId, book);
        log.info("Successfully deleted edition at index: {}", editionIndex);
    }

    @Override
    public Edition getEdition(@NonNull Long bookId, int editionIndex) {
        log.debug("Fetching edition at index {} from book {}", editionIndex, bookId);
        Book book = getBookById(bookId);

        if (book.getEditions() == null) {
            log.warn("No editions found for book {}", bookId);
            throw new ResourceNotFoundException("No editions exist for book with ID: " + bookId);
        } else if (editionIndex < 0) {
            log.warn("Negative edition index ({}) for book {}", editionIndex, bookId);
            throw new InvalidArgumentsException("Edition index cannot be negative: " + editionIndex);
        } else if (editionIndex >= book.getEditions().size()) {
            log.warn("Edition index out of range ({}) for book {}", editionIndex, bookId);
            throw new InvalidArgumentsException("No edition found at index: " + editionIndex + " for book with ID: " + bookId);
        }

        Edition edition = book.getEditions().get(editionIndex);
        log.debug("Successfully found edition at index: {}", editionIndex);
        return edition;
    }

    @Override
    public List<Edition> getAllEditionsFromBook(@NonNull Long bookId) {
        log.debug("Fetching all editions from book {}", bookId);
        Book book = getBookById(bookId);
        return book.getEditions() != null ? book.getEditions() : new ArrayList<>();
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

        // Validacion fecha de publicacion del libro
        int currentYear = java.time.Year.now().getValue();
        if (book.getPublicationYear() != null && book.getPublicationYear() > currentYear) {
            log.warn("Published date is in the future for book: {}", book.getTitle());
            throw new InvalidArgumentsException("Published date cannot be in the future");
        }

        // Validar las ediciones si existen
        if (book.getEditions() != null) {
            for (int i = 0; i < book.getEditions().size(); i++) {
                Edition edition = book.getEditions().get(i);
                String editionPrefix = "Edition " + i + ": ";

                // Validacion formato isbn
                if (edition.getIsbn() != null && !edition.getIsbn().trim().isEmpty()) {
                    String isbn = edition.getIsbn().replaceAll("[^0-9X]", "");
                    if (isbn.length() != 10 && isbn.length() != 13) {
                        log.warn("{}Invalid ISBN format: {}", editionPrefix, edition.getIsbn());
                        throw new InvalidArgumentsException(editionPrefix + "ISBN must be 10 or 13 digits");
                    }
                }

                // Validaciones con numeros de copias
                if (edition.getTotalCopies() != null && edition.getTotalCopies() < 0) {
                    log.warn("{}Invalid total copies: {}", editionPrefix, edition.getTotalCopies());
                    throw new InvalidArgumentsException(editionPrefix + "Total copies cannot be negative");
                }
                if (edition.getSoldCopies() != null && edition.getSoldCopies() < 0) {
                    log.warn("{}Invalid sold copies: {}", editionPrefix, edition.getSoldCopies());
                    throw new InvalidArgumentsException(editionPrefix + "Sold copies cannot be negative");
                }
                if (edition.getTotalCopies() != null && edition.getSoldCopies() != null && edition.getSoldCopies() > edition.getTotalCopies()) {
                    log.warn("{}Sold copies ({}) exceed total copies ({})", editionPrefix, edition.getSoldCopies(), edition.getTotalCopies());
                    throw new InvalidArgumentsException(editionPrefix + "Sold copies cannot exceed total copies");
                }

                // Validacion fecha de publicacion de la edicion
                if (edition.getPublishedDate() != null && edition.getPublishedDate().isAfter(java.time.LocalDate.now())) {
                    log.warn("{}Published date is in the future", editionPrefix);
                    throw new InvalidArgumentsException(editionPrefix + "Published date cannot be in the future");
                }

                // Validacion poginas
                if (edition.getPages() != null && edition.getPages() <= 0) {
                    log.warn("{}Invalid page count: {}", editionPrefix, edition.getPages());
                    throw new InvalidArgumentsException(editionPrefix + "Page count must be positive");
                }
            }
        }
    }

    public void logBookDetails(String prefix, Book book) {
        if (log.isDebugEnabled()) {
            log.debug("{}: title: {}, authorIds: {}, publishedDate: {}, authorPseudonyms: {}, editions: {}",
                prefix,
                book.getTitle(),
                book.getAuthorIds(),
                book.getPublicationYear() != null ? book.getPublicationYear() : "Unknown published date",
                book.getAuthorPseudonyms() != null && !book.getAuthorPseudonyms().isEmpty() ? book.getAuthorPseudonyms() : "No pseudonyms",
                book.getEditions() != null ? book.getEditions() : "No editions yet");
        }
    }
}
