package edu.otus.spring02.service;

import edu.otus.spring02.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AuthorsService {
    <T> List<T> getAuthors(Function<Author, T> mapper);
    <T> Optional<T> getAuthor(String id, Function<Author, T> mapper);
    String createAuthor(String name);
}
