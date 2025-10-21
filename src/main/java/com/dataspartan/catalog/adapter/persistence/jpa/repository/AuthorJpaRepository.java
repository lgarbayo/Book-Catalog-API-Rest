package com.dataspartan.catalog.adapter.persistence.jpa.repository;

import com.dataspartan.catalog.adapter.persistence.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, Long> {
}