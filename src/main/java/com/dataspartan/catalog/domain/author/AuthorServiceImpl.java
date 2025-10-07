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
        log.debug("Author details: surname: {}, birthYear: {}", author.getSurname(), author.getBirthYear());
        
        // Validacion del nombre
        if (author.getName() == null || author.getName().trim().isEmpty()) { // Validacion necesaria ya que @NonNull no lo garantiza en deserialización JSON
            log.warn("Attempt to create author with null or empty name");
            // sin la validacion de == null salta error 500
            throw new InvalidArgumentsException("Name is required and cannot be empty");
        }
        
        // Asegurar que es un autor nuevo (sin ID)
        log.debug("Setting author ID to null for new creation");
        author.setId(null);
        
        // Guardar en repositorio
        Author savedAuthor = authorRepository.save(author);
        log.info("Successfully created author with ID: {}", savedAuthor.getId());
        log.debug("Saved author details: name: {}, surname: {}, birthYear: {}", 
                 savedAuthor.getName(), savedAuthor.getSurname(), savedAuthor.getBirthYear());
        
        return savedAuthor;
    }

    @Override
    public Author updateAuthor(@NonNull Long id, @NonNull Author author) {
        log.info("Updating author with ID: {}", id);
        log.debug("Update request details: name: {}, surname: {}, birthYear: {}", 
                 author.getName(), author.getSurname(), author.getBirthYear());
        
        // Verificar que el autor existe
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor == null) {
            log.warn("Attempt to update non-existent author with ID: {}", id);
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        
        log.debug("Found existing author: name: {}, surname: {}", 
                 existingAuthor.getName(), existingAuthor.getSurname());

        // Validacion del nombre
        if (author.getName() == null || author.getName().trim().isEmpty()) { // Validacion necesaria ya que @NonNull no lo garantiza en deserialización JSON
            log.warn("Attempt to update author {} with null or empty name", id);
            // sin la validacion de == null salta error 500
            throw new InvalidArgumentsException("Name is required and cannot be empty");
        }
        
        // Actualizar el autor en el repositorio
        log.info("Successfully updated author with ID: {}", id);
        log.debug("Updated author details: name: {}, surname: {}, birthYear: {}", 
                 authorRepository.update(id, author).getName(), authorRepository.update(id, author).getSurname(), authorRepository.update(id, author).getBirthYear());
        return authorRepository.update(id, author);
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
                 existingAuthor.getName(), existingAuthor.getSurname());

        log.debug("Proceeding with author deletion (referential integrity checked at Facade layer)");
        boolean deleted = authorRepository.deleteById(id);

        if (!deleted) {
            log.error("Failed to delete author with ID: {}", id);
            throw new PreconditionFailedException("Failed to delete author with id: " + id);
        }

        log.info("Successfully deleted author with ID: {}", id);
    }
}
