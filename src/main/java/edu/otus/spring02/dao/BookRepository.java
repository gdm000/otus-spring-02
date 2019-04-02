package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getBooks();

    Optional<Book> getBook(int id);

    int createBook(Book prototype);
}
