package com.dataspartan.catalog.adapter.persistence.jpa.adapter;

import com.dataspartan.catalog.domain.book.Book;
import com.dataspartan.catalog.domain.book.BookRepository;
import com.dataspartan.catalog.adapter.persistence.jpa.repository.BookJpaRepository;
import com.dataspartan.catalog.adapter.persistence.entity.BookEntity;
import com.dataspartan.catalog.adapter.persistence.mapper.BookMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaBookRepositoryAdapter implements BookRepository {

    private final BookJpaRepository jpaRepository;
    private final BookMapper mapper;

    public JpaBookRepositoryAdapter(BookJpaRepository jpaRepository, BookMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Book> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public Book findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain).orElse(null);
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = mapper.toEntity(book);
        BookEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Book update(Long id, Book book) {
        if (!jpaRepository.existsById(id)) {
            return null;
        }
        book.setId(id);
        return save(book);
    }

    @Override
    public boolean deleteById(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> findByAuthorsId(Long authorId) {
        return mapper.toDomainList(jpaRepository.findByAuthorsId(authorId));
    }
}