package com.dataSpartan.catalog.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dataSpartan.catalog.domain.model.Book;

@Repository
public class BookRepository {

    // Almacenamiento en memoria usando Map<key, value> para búsquedas eficientes por ID

    /*
     * Spring Data repositories típicamente tienen:
     * save(T entity)     Crear/actualizar
     * findById(ID id)    Buscar por ID
     * findAll()          Listar todos
     * deleteById(ID id)  Eliminar
    */

    /*
     * Operaciones CRUD básicas
     * Create  → save()
     * Read    → findById(), findAll()
     * Update  → update() 
     * Delete  → deleteById()
     */
    
    private final Map<Long, Book> books = new HashMap<>();
    private Long nextId = 1L;

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Book findById(Long id) {
        return books.get(id);
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId++);
        }
        books.put(book.getId(), book);
        return book;
    }

    public Book update(Long id, Book book) {
        if (books.containsKey(id)) {
            book.setId(id); // Asegurar que mantiene el ID correcto
            books.put(id, book);
            return book;
        }
        return null; // No encontrado
    }

    public boolean deleteById(Long id) {
        return books.remove(id) != null;
    }

    // Método útil para buscar libros por autor (para la regla de negocio)
    public List<Book> findBooksByAuthorId(Long authorId) {
        return books.values().stream()
                .filter(book -> book.getAuthors().stream()
                        .anyMatch(author -> author.getId().equals(authorId)))
                .toList();
    }
}
