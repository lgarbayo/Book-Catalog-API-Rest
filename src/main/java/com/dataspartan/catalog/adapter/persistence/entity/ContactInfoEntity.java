package com.dataspartan.catalog.adapter.persistence.entity;

import com.dataspartan.catalog.domain.author.ContactType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ContactInfoEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type")
    private ContactType type;

    @Column(name = "contact_value")
    private String value;
}