package edu.otus.spring02.service;

import edu.otus.spring02.domain.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface GenresService {

    <T> Flux<T> getGenres(Function<Genre, T> mapper);
    <T> Mono<T> getGenre(String id, Function<Genre, T> mapper);
    Mono<String> createGenre(String name, String description);

}
