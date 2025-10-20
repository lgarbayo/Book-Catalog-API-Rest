package com.dataspartan.catalog.domain.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {
    private ContactType type;
    private String value;
}
