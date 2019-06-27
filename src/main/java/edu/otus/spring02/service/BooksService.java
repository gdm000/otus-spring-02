package edu.otus.spring02.service;

import edu.otus.spring02.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface BooksService {
    <T> Flux<T> getBooks(Function<Book, T> mapper);
    <T> Mono<T> getBook(String id, Function<Book, T> mapper);
    Mono<String> createBook(String name, String authorId, String genreId);
    <T> Mono<T> updateBook(String id, String name, String authorId, String genreId, Function<Book, T> mapper);
    Mono<Void> deleteBook(String bookId);
}
