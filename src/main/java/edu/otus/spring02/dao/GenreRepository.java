package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> getGenres();
    Optional<Genre> getGenre(int id);
    int createGenre(Genre prototype);
}
