package com.dataspartan.catalog.adapter.persistence.mapper;

import com.dataspartan.catalog.domain.author.Author;
import com.dataspartan.catalog.domain.author.ContactInfo;
import com.dataspartan.catalog.adapter.persistence.entity.AuthorEntity;
import com.dataspartan.catalog.adapter.persistence.entity.ContactInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    public Author toDomain(AuthorEntity entity) {
        if (entity == null) return null;

        List<ContactInfo> contactInfoList = entity.getContactInfo() != null
                ? entity.getContactInfo().stream()
                .map(this::toDomain)
                .collect(Collectors.toList())
                : List.of();

        return new Author(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getNationality(),
                entity.getBirthDate(),
                entity.getDeathDate(),
                entity.getBiography(),
                entity.getPseudonyms(),
                contactInfoList
        );
    }

    public AuthorEntity toEntity(Author domain) {
        if (domain == null) return null;

        AuthorEntity entity = new AuthorEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setSurname(domain.getSurname());
        entity.setNationality(domain.getNationality());
        entity.setBirthDate(domain.getBirthDate());
        entity.setDeathDate(domain.getDeathDate());
        entity.setBiography(domain.getBiography());
        entity.setPseudonyms(domain.getPseudonyms());

        List<ContactInfoEntity> contactInfoList = domain.getContactInfo() != null
                ? domain.getContactInfo().stream()
                .map(this::toEntity)
                .collect(Collectors.toList())
                : List.of();

        entity.setContactInfo(contactInfoList);
        return entity;
    }

    private ContactInfo toDomain(ContactInfoEntity entity) {
        return new ContactInfo(entity.getType(), entity.getValue());
    }

    private ContactInfoEntity toEntity(ContactInfo domain) {
        return new ContactInfoEntity(domain.getType(), domain.getValue());
    }

    public List<Author> toDomainList(List<AuthorEntity> entities) {
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }
}