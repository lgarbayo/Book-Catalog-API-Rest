//Puerto de salida

package com.dataspartan.catalog.domain.author;

import java.util.List;

public interface AuthorRepository {
    
    List<Author> findAll();
    Author findById(Long id);
    Author save(Author author);
    Author update(Long id, Author author);
    boolean deleteById(Long id);
}
