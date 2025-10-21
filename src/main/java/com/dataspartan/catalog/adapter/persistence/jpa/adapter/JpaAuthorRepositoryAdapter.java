package com.dataspartan.catalog.adapter.persistence.jpa.adapter;

import com.dataspartan.catalog.domain.author.Author;
import com.dataspartan.catalog.domain.author.AuthorRepository;
import com.dataspartan.catalog.adapter.persistence.jpa.repository.AuthorJpaRepository;
import com.dataspartan.catalog.adapter.persistence.entity.AuthorEntity;
import com.dataspartan.catalog.adapter.persistence.mapper.AuthorMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaAuthorRepositoryAdapter implements AuthorRepository {

    private final AuthorJpaRepository jpaRepository;
    private final AuthorMapper mapper;

    public JpaAuthorRepositoryAdapter(AuthorJpaRepository jpaRepository, AuthorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Author> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public Optional<Author> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Author save(Author author) {
        AuthorEntity entity = mapper.toEntity(author);
        AuthorEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Author update(Long id, Author author) {
        if (!jpaRepository.existsById(id)) {
            return null;
        }
        author.setId(id);
        return save(author);
    }

    @Override
    public boolean deleteById(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}