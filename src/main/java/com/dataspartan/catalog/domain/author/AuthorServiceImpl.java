package com.dataspartan.catalog.domain.author;

import java.util.List;

import com.dataspartan.catalog.exception.InvalidArgumentsException;
import com.dataspartan.catalog.exception.PreconditionFailedException;
import com.dataspartan.catalog.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        log.debug("Fetching all authors. Total count: {}", authorRepository.findAll().size());
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(@NonNull Long id) {
        log.debug("Fetching author with ID: {}", id);
        Author author = authorRepository.findById(id);
        if (author == null) {
            log.warn("Author not found with ID: {}", id);
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        log.debug("Author found: {}", author.getName());
        return author;
    }

    @Override
    public Author createAuthor(@NonNull Author author) {
        log.info("Creating new author with name: {}", author.getName());
        logAuthorDetails("Author details", author);

        validateAuthor(author);

        // Asegurar que es un autor nuevo (sin ID)
        author.setId(null);

        Author savedAuthor = authorRepository.save(author);
        log.info("Successfully created author with ID: {}", savedAuthor.getId());
        return savedAuthor;
    }

    @Override
    public Author updateAuthor(@NonNull Long id, @NonNull Author author) {
        log.info("Updating author with ID: {}", id);
        logAuthorDetails("Update request details", author);

        // Verificar que el autor existe
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor == null) {
            log.warn("Attempt to update non-existent author with ID: {}", id);
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }

        validateAuthor(author);

        Author updatedAuthor = authorRepository.update(id, author);
        log.info("Successfully updated author with ID: {}", id);
        logAuthorDetails("Updated author details", updatedAuthor);

        return updatedAuthor;
    }

    @Override
    public void deleteAuthor(@NonNull Long id) {
        log.info("Attempting to delete author with ID: {}", id);
        
        // Verificar que el autor existe
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor == null) {
            log.warn("Attempt to delete non-existent author with ID: {}", id);
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        
        log.debug("Found author to delete: name: {}, surname: {}", 
                 existingAuthor.getName(),
                 existingAuthor.getSurname() != null ? existingAuthor.getSurname() : "No surname");

        boolean deleted = authorRepository.deleteById(id);

        if (!deleted) {
            log.error("Failed to delete author with ID: {}", id);
            throw new PreconditionFailedException("Failed to delete author with id: " + id);
        }

        log.info("Successfully deleted author with ID: {}", id);
    }

    private void validateAuthor(Author author) {
        // Validacion del nombre
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            log.warn("Attempt to create/update author with null or empty name");
            throw new InvalidArgumentsException("Name is required and cannot be empty");
        }

        // Validaciones fechas
        if (author.getBirthDate() != null && author.getBirthDate().isAfter(java.time.LocalDate.now())) {
            log.warn("Birth date is in the future for author: {}", author.getName());
            throw new InvalidArgumentsException("Birth date cannot be in the future");
        }

        if (author.getBirthDate() != null && author.getDeathDate() != null) {
            if (author.getBirthDate().isAfter(author.getDeathDate())) {
                log.warn("Birth date is after death date for author: {}", author.getName());
                throw new InvalidArgumentsException("Birth date cannot be after death date");
            }
            if (author.getDeathDate().isAfter(java.time.LocalDate.now())) {
                log.warn("Death date is in the future for author: {}", author.getName());
                throw new InvalidArgumentsException("Death date cannot be in the future");
            }
        }
    }

    public void logAuthorDetails(String prefix, Author author) {
        if (log.isDebugEnabled()) {
            log.debug("{}: name: {}, surname: {}, nationality: {}, birthDate: {}, deathDate: {}, biography: {}",
                    prefix, // Texto del log
                    author.getName(),
                    author.getSurname() != null ? author.getSurname() : "No surname",
                    author.getNationality() != null ? author.getNationality() : "Unknown nationality",
                    author.getBirthDate() != null ? author.getBirthDate() : "Unknown birth date",
                    author.getDeathDate() != null ? author.getDeathDate() : "Unknown death date",
                    author.getBiography() != null ? author.getBiography() : "No biography");
        }
    }
}
