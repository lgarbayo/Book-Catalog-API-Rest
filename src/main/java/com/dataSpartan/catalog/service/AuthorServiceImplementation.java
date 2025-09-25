package com.dataSpartan.catalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dataSpartan.catalog.domain.model.Author;
import com.dataSpartan.catalog.domain.model.Book;
import com.dataSpartan.catalog.repository.AuthorRepository;
import com.dataSpartan.catalog.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {
    
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        Author author = authorRepository.findById(id);
        // Verificar que el autor existe
        if (author == null) {
            throw new IllegalArgumentException("Author not found with id: " + id);
        }
        return author;
    }

    @Override
    public Author createAuthor(Author author) {
        // Verificar que el autor no es nulo y tiene nombre
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name is required");
        }
        
        // Asegurar que es un autor nuevo (sin ID)
        author.setId(null);
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        // Verificar que el autor existe
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor == null) {
            throw new IllegalArgumentException("Author not found with id: " + id);
        }

        // Verificar que el autor no es nulo y tiene nombre
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name is required");
        }

        // Actualizar el autor en el repositorio
        return authorRepository.update(id, author);
    }

    @Override
    public void deleteAuthor(Long id) {
        // Verificar que el autor existe
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor == null) {
            throw new IllegalArgumentException("Author not found with id: " + id);
        }

        // Important note: do not allow the removal of an author when at least one book is related to it.
        
        // Paso 1: recuperamos todos los libros
        List<Book> books = bookRepository.findAll();
        
        // Paso 2: buscar en la lista
        for (Book book : books) {
            List<Author> authorsInBook = book.getAuthors();
            for (Author author : authorsInBook) {
                if (author.getId().equals(id)) {
                    throw new IllegalArgumentException("Cannot delete author in book: " + book.getTitle() + " with id: " + id);
                }
            }
        }

        // Si llegamos aquí, el autor no está en ningún libro
        authorRepository.deleteById(id);
    }
}
