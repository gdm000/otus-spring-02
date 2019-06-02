package edu.otus.spring02.service;

import edu.otus.spring02.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface BooksService {
    <T> List<T> getBooks(Function<Book, T> mapper);
    <T> Optional<T> getBook(String id, Function<Book, T> mapper);
    String createBook(String name, String authorId, String genreId);
    <T> T updateBook(String id, String name, String authorId, String genreId, Function<Book, T> mapper);
    void deleteBook(String bookId);
}
