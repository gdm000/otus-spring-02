package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> getAuthors();
    Optional<Author> getAuthor(int id);
    int createAuthor(Author prototype);
}
