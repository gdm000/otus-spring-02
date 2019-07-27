package edu.otus.spring02.service;

import edu.otus.spring02.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface GenresService {

    <T> List<T> getGenres(Function<Genre, T> mapper);
    <T> Optional<T> getGenre(String id, Function<Genre, T> mapper);
    String createGenre(String name, String description);

}
