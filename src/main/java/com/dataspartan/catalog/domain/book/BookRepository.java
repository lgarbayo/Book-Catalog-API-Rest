package com.dataspartan.catalog.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // JpaRepository ya provee: findAll(), findById(), save(), deleteById()

    @Query("SELECT DISTINCT b FROM Book b JOIN b.authorIds aid WHERE aid = :authorId")
    List<Book> findByAuthorsId(@Param("authorId") Long authorId);
}
