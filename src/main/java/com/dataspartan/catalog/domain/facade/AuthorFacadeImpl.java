package com.dataspartan.catalog.domain.facade;

import com.dataspartan.catalog.domain.author.AuthorService;
import com.dataspartan.catalog.domain.book.Book;
import com.dataspartan.catalog.domain.book.BookService;
import com.dataspartan.catalog.exception.PreconditionFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorFacadeImpl implements AuthorFacade{

    private final AuthorService authorService;
    private final BookService bookService;

    @Override
    public void deleteAuthor(Long authorId) {
        List<Book> books = bookService.findBooksByAuthorId(authorId);

        if (!books.isEmpty()) {
            throw new PreconditionFailedException("Cannot delete author: author has associated books");
        }

        authorService.deleteAuthor(authorId);
    }
}
