package com.dataspartan.catalog.adapter.persistence.mapper;

import com.dataspartan.catalog.domain.book.Book;
import com.dataspartan.catalog.domain.book.Edition;
import com.dataspartan.catalog.adapter.persistence.entity.BookEntity;
import com.dataspartan.catalog.adapter.persistence.entity.EditionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book toDomain(BookEntity entity) {
        if (entity == null) return null;

        List<Edition> editionList = entity.getEditions() != null
                ? entity.getEditions().stream()
                .map(this::toDomain)
                .collect(Collectors.toList())
                : List.of();

        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthorIds(),
                entity.getPublicationYear(),
                entity.getAuthorPseudonyms(),
                editionList
        );
    }

    public BookEntity toEntity(Book domain) {
        if (domain == null) return null;

        BookEntity entity = new BookEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setAuthorIds(domain.getAuthorIds());
        entity.setPublicationYear(domain.getPublicationYear());
        entity.setAuthorPseudonyms(domain.getAuthorPseudonyms());

        List<EditionEntity> editionList = domain.getEditions() != null
                ? domain.getEditions().stream()
                .map(this::toEntity)
                .collect(Collectors.toList())
                : List.of();

        entity.setEditions(editionList);
        return entity;
    }

    private Edition toDomain(EditionEntity entity) {
        return new Edition(
                entity.getIsbn(),
                entity.getPublisher(),
                entity.getPublishedDate(),
                entity.getCountry(),
                entity.getLanguage(),
                entity.getPages(),
                entity.getTotalCopies(),
                entity.getSoldCopies(),
                entity.getDescription()
        );
    }

    private EditionEntity toEntity(Edition domain) {
        return new EditionEntity(
                domain.getIsbn(),
                domain.getPublisher(),
                domain.getPublishedDate(),
                domain.getCountry(),
                domain.getLanguage(),
                domain.getPages(),
                domain.getTotalCopies(),
                domain.getSoldCopies(),
                domain.getDescription()
        );
    }

    public List<Book> toDomainList(List<BookEntity> entities) {
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }
}