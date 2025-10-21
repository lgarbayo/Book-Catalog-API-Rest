package com.dataspartan.catalog.adapter.persistence.jpa.repository;

import com.dataspartan.catalog.adapter.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authorIds aid WHERE aid = :authorId")
    List<BookEntity> findByAuthorsId(@Param("authorId") Long authorId);
}