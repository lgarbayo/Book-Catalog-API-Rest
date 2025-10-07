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

import com.dataspartan.catalog.domain.author.Author;
import com.dataspartan.catalog.domain.author.AuthorService;
import com.dataspartan.catalog.domain.facade.AuthorFacadeImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorFacadeImpl authorFacade;

    // Endpoints
    @GetMapping
    public List<Author> getAllAuthors() {
        log.info("GET /author - Retrieving all authors");
        List<Author> authors = authorService.getAllAuthors();
        log.debug("Retrieved {} authors", authors.size());
        return authors;
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        log.info("GET /author/{} - Retrieving author by ID", id);
        Author author = authorService.getAuthorById(id);
        authorService.logAuthorDetails("Retrieved author", author);
        return author;
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        log.info("POST /author - Creating new author with name: {}", author.getName());
        authorService.logAuthorDetails("Author creation request", author);
        Author createdAuthor = authorService.createAuthor(author);
        log.info("Successfully created author with ID: {}", createdAuthor.getId());
        return createdAuthor;
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        log.info("PUT /author/{} - Updating author", id);
        authorService.logAuthorDetails("Author update request", author);
        Author updatedAuthor = authorService.updateAuthor(id, author);
        log.info("Successfully updated author with ID: {}", id);
        return updatedAuthor;
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        log.info("DELETE /author/{} - Deleting author", id);
        authorFacade.deleteAuthor(id);
        log.info("Successfully deleted author with ID: {}", id);
    }
    
}