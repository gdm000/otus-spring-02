package edu.otus.spring02.dao;

import edu.otus.spring02.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
}
