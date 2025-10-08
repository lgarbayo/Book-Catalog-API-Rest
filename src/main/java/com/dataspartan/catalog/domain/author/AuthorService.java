package com.dataspartan.catalog.domain.author;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author createAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);
    void logAuthorDetails(String prefix, Author author);

    // CRUD operations for nested ContactInfo elements
    ContactInfo addContactInfoToAuthor(Long authorId, ContactInfo contactInfo);
    ContactInfo updateContactInfo(Long authorId, int contactIndex, ContactInfo contactInfo);
    void deleteContactInfo(Long authorId, int contactIndex);
    ContactInfo getContactInfo(Long authorId, int contactIndex);
    List<ContactInfo> getAllContactInfoFromAuthor(Long authorId);
}
