package edu.otus.spring02.service;

import edu.otus.spring02.domain.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface AuthorsService {
    <T> Flux<T> getAuthors(Function<Author, T> mapper);
    <T> Mono<T> getAuthor(String id, Function<Author, T> mapper);
    Mono<String> createAuthor(String name);
}
