package com.dataspartan.catalog.domain.author;

import java.util.ArrayList;
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
        Author author = authorRepository.findById(id).orElse(null);
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
        if (!authorRepository.existsById(id)) {
            log.warn("Attempt to update non-existent author with ID: {}", id);
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }

        validateAuthor(author);

        // Asegurar que mantiene el ID correcto
        author.setId(id);
        Author updatedAuthor = authorRepository.save(author);
        log.info("Successfully updated author with ID: {}", id);
        logAuthorDetails("Updated author details", updatedAuthor);

        return updatedAuthor;
    }

    @Override
    public void deleteAuthor(@NonNull Long id) {
        log.info("Attempting to delete author with ID: {}", id);
        
        // Verificar que el autor existe
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        if (existingAuthor == null) {
            log.warn("Attempt to delete non-existent author with ID: {}", id);
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        
        log.debug("Found author to delete: name: {}, surname: {}", 
                 existingAuthor.getName(),
                 existingAuthor.getSurname() != null ? existingAuthor.getSurname() : "No surname");

        try {
            authorRepository.deleteById(id);
            log.info("Successfully deleted author with ID: {}", id);
        } catch (Exception e) {
            log.error("Failed to delete author with ID: {}", id, e);
            throw new PreconditionFailedException("Failed to delete author with id: " + id);
        }
    }

    // CRUD operations for nested ContactInfo elements
    @Override
    public ContactInfo addContactInfoToAuthor(@NonNull Long authorId, @NonNull ContactInfo contactInfo) {
        log.info("Adding contact info to author with ID: {}", authorId);
        Author author = getAuthorById(authorId);

        if (author.getContactInfo() == null) {
            author.setContactInfo(new ArrayList<>());
        }

        author.getContactInfo().add(contactInfo);
        authorRepository.save(author);
        log.info("Successfully added contact info to author: {}", authorId);
        return contactInfo;
    }

    @Override
    public ContactInfo updateContactInfo(@NonNull Long authorId, int contactIndex, @NonNull ContactInfo contactInfo) {
        log.info("Updating contact info at index {} for author {}", contactIndex, authorId);
        Author author = getAuthorById(authorId);

        if (author.getContactInfo() == null) {
            log.warn("Contact info list is null for author: {}", authorId);
            throw new ResourceNotFoundException("Contact info list is not initialized for author: " + authorId);
        }
        if (contactIndex < 0) {
            log.warn("Negative contact info index: {} for author: {}", contactIndex, authorId);
            throw new InvalidArgumentsException("Contact info index cannot be negative: " + contactIndex);
        }
        if (contactIndex >= author.getContactInfo().size()) {
            log.warn("Contact info index out of bounds: {} for author: {}", contactIndex, authorId);
            throw new InvalidArgumentsException("Contact info index out of bounds: " + contactIndex);
        }

        author.getContactInfo().set(contactIndex, contactInfo);
        authorRepository.save(author);
        log.info("Successfully updated contact info at index: {}", contactIndex);
        return contactInfo;
    }

    @Override
    public void deleteContactInfo(@NonNull Long authorId, int contactIndex) {
        log.info("Deleting contact info at index {} from author {}", contactIndex, authorId);
        Author author = getAuthorById(authorId);

        if (author.getContactInfo() == null) {
            log.warn("Contact info list is null for author: {}", authorId);
            throw new ResourceNotFoundException("Contact info list is not initialized for author: " + authorId);
        }
        if (contactIndex < 0) {
            log.warn("Negative contact info index: {} for author: {}", contactIndex, authorId);
            throw new InvalidArgumentsException("Contact info index cannot be negative: " + contactIndex);
        }
        if (contactIndex >= author.getContactInfo().size()) {
            log.warn("Contact info index out of bounds: {} for author: {}", contactIndex, authorId);
            throw new InvalidArgumentsException("Contact info index out of bounds: " + contactIndex);
        }

        author.getContactInfo().remove(contactIndex);
        authorRepository.save(author);
        log.info("Successfully deleted contact info at index: {}", contactIndex);
    }

    @Override
    public ContactInfo getContactInfo(@NonNull Long authorId, int contactIndex) {
        log.debug("Fetching contact info at index {} from author {}", contactIndex, authorId);
        Author author = getAuthorById(authorId);

        if (author.getContactInfo() == null) {
            log.warn("Contact info list is null for author: {}", authorId);
            throw new ResourceNotFoundException("Contact info list is not initialized for author: " + authorId);
        }
        if (contactIndex < 0) {
            log.warn("Negative contact info index: {} for author: {}", contactIndex, authorId);
            throw new InvalidArgumentsException("Contact info index cannot be negative: " + contactIndex);
        }
        if (contactIndex >= author.getContactInfo().size()) {
            log.warn("Contact info index out of bounds: {} for author: {}", contactIndex, authorId);
            throw new InvalidArgumentsException("Contact info index out of bounds: " + contactIndex);
        }

        return author.getContactInfo().get(contactIndex);
    }

    @Override
    public List<ContactInfo> getAllContactInfoFromAuthor(@NonNull Long authorId) {
        log.debug("Fetching all contact info from author {}", authorId);
        Author author = getAuthorById(authorId);
        return author.getContactInfo() != null ? author.getContactInfo() : new ArrayList<>();
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
            log.debug("{}: name: {}, surname: {}, nationality: {}, birthDate: {}, deathDate: {}, biography: {}, pseudonyms: {}, contactInfo: {}",
                    prefix,
                    author.getName(),
                    author.getSurname() != null ? author.getSurname() : "No surname",
                    author.getNationality() != null ? author.getNationality() : "Unknown nationality",
                    author.getBirthDate() != null ? author.getBirthDate() : "Unknown birth date",
                    author.getDeathDate() != null ? author.getDeathDate() : "Unknown death date",
                    author.getBiography() != null ? author.getBiography() : "No biography",
                    author.getPseudonyms() != null ? author.getPseudonyms() : "No pseudonyms",
                    author.getContactInfo() != null ? author.getContactInfo(): "No contact info");
        }
    }
}
